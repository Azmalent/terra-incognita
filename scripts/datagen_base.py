import os

MODID = 'terraincognita'

CWD = os.getcwd()
ASSETS_TEMPLATE_DIR = CWD + '/templates/assets/'
DATA_TEMPLATE_DIR = CWD + '/templates/data/'
ASSETS_DIR = CWD + '/../src/main/resources/assets/%s/' % MODID
DATA_DIR = CWD + '/../src/main/resources/data/%s/' % MODID

assert(os.path.isdir(ASSETS_TEMPLATE_DIR))
assert(os.path.isdir(DATA_TEMPLATE_DIR))
assert(os.path.isdir(ASSETS_DIR))
assert(os.path.isdir(DATA_DIR))

def copy_file(template, out_file, vars):
    template += '.json'
    out_file += '.json'

    assert(os.path.isfile(template))

    if os.path.isfile(out_file):
        return

    out_dir = os.path.dirname(out_file)
    os.makedirs(out_dir, exist_ok=True)

    with open(template, 'rt') as fin:
        with open(out_file, 'wt') as fout:
            for line in fin:
                string = line.replace('$MODID', MODID)
                for k in vars:
                    string = string.replace('$' + k.upper(), vars[k])
                if '$' in string:
                    _, filename = os.path.split(out_file)
                    print('WARNING: missing variable in line "%s" in file "%s"' % (string.strip(), filename))
                fout.write(string)


def to_tag_entry(item):
    if item[0] == '#':
        return item

    return '{"id": "%s", "required": false}' % item

def add_to_tag(tag_file, *items):
    if os.path.isfile(tag_file):
        with open(tag_file, 'rt') as f:
            contents = f.readlines()
    else:
        os.makedirs(os.path.dirname(out_file), exist_ok=True)
        with open(DATA_TEMPLATE_DIR + 'tags/empty_tag.json', 'rt') as f:
            contents = f.readlines()

    header, lines, footer = contents[:3], contents[3:-2], contents[-2:]
    new_items = list(map(to_tag_entry, items))
    old_items = list(map(lambda l: l.rstrip(',')))

    all_items = list(set(items + lines))
    all_items.sort()

    with open(tag_file, 'wt') as f:
        for line in header:
            f.write(line)

        f.write(',\n'.join(all_items))

        for line in footer:
            f.write(line)


def add_to_item_tag(tag, *items, namespace=MODID):
    filename = CWD + '/../src/main/resources/data/%s/tags/items/%s.json' % (namespace, tag)
    add_to_tag(filename, *items)


def add_to_block_tag():
    filename = CWD + '/../src/main/resources/data/%s/tags/blocks/%s.json' % (namespace, tag)
    add_to_tag(filename, *items)


def add_to_item_and_block_tags(tag, *items, namespace=MODID):
    add_to_item_tag(tag, *items, namespace=namespace)
    add_to_block_tag(tag, *items, namespace=namespace)


def copy_asset(path, template, out_file, vars):
    copy_file('%s/%s/%s' % (ASSETS_TEMPLATE_DIR, path, template), '%s/%s/%s' % (ASSETS_DIR, path, out_file), vars)


def copy_blockstate(template, out_file, vars):
    copy_asset('blockstates', template, out_file, vars)


def copy_block_model(template, out_file, vars, block_model_suffixes=[]):
    for suffix in block_model_suffixes:
        copy_asset('models/block', template + suffix, out_file + suffix, vars)


def copy_item_model(template, out_file, vars):
    copy_asset('models/item', template, out_file, vars)


def copy_blockstate_and_models(template, out_file, vars, block_model_suffixes=[], item_model_suffix=''):
    copy_blockstate(template, out_file, vars)
    copy_block_model(template, out_file, vars, block_model_suffixes=block_model_suffixes)

    if item_model_suffix == '' and '_inventory' in block_model_suffixes:
        item_model_suffix = '_inventory'

    if 'block' in vars:
        vars['block'] += item_model_suffix

    copy_item_model('block', out_file, vars)


def copy_data(path, template, out_file, vars):
    copy_file('%s/%s/%s' % (DATA_TEMPLATE_DIR, path, template), '%s/%s/%s' % (DATA_DIR, path, out_file), vars)


def copy_loot_table(template, out_file, vars):
    copy_data('loot_tables/blocks', template, out_file, vars)


def drop_itself(out_file, vars):
    copy_loot_table('drop_itself', out_file, vars)


def copy_recipe(template, out_file, vars):
    copy_data('recipes', template, out_file, vars)


def copy_crafting_recipe(template, out_file, vars):
    copy_recipe(template, 'crafting/' + out_file, vars)


def copy_block_tag(template, out_file, vars):
    copy_data('tags', template, 'blocks/' + out_file, vars)


def copy_item_tag(template, out_file, vars):
    copy_data('tags', template, 'items/' + out_file, vars)