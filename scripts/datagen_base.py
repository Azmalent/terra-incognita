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


def copy_file(template, out_file, variables):
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
                for k in variables:
                    string = string.replace('$' + k.upper(), variables[k])
                if '$' in string:
                    _, filename = os.path.split(out_file)
                    print('WARNING: missing variable in line "%s" in file "%s"' % (string.strip(), filename))
                fout.write(string)


def to_tag_entry(item, optional=False):
    if not optional or item[0] == '#':
        return '"%s"' % item

    return '{"id": "%s", "required": false}' % item


def add_to_tag(tag_file, *items, optional=False):
    if os.path.isfile(tag_file):
        with open(tag_file, 'rt') as f:
            contents = f.readlines()
    else:
        os.makedirs(os.path.dirname(tag_file), exist_ok=True)
        with open(DATA_TEMPLATE_DIR + 'tags/empty_tag.json', 'rt') as f:
            contents = f.readlines()

    header, lines, footer = contents[:3], contents[3:-2], contents[-2:]
    new_items = [to_tag_entry(x, optional=optional) for x in items]
    old_items = [x.strip(', \t\n') for x in lines]

    all_items = ['\t' + x for x in set(new_items + old_items) if x]
    all_items.sort()

    tags = []
    while len(all_items) > 0 and '#' in all_items[0]:
        tags.append(all_items.pop(0))

    all_items += tags

    with open(tag_file, 'wt') as f:
        for line in header:
            f.write(line)

        f.write(',\n'.join(all_items) + '\n')

        for line in footer:
            f.write(line)


def add_to_item_tag(tag, *items, namespace='minecraft', optional=False):
    filename = CWD + '/../src/main/resources/data/%s/tags/items/%s.json' % (namespace, tag)
    add_to_tag(filename, *items, optional=optional)


def add_to_block_tag(tag, *items, namespace='minecraft', optional=False):
    filename = CWD + '/../src/main/resources/data/%s/tags/blocks/%s.json' % (namespace, tag)
    add_to_tag(filename, *items, optional=optional)


def add_to_item_and_block_tags(tag, *items, namespace='minecraft', optional=False):
    add_to_item_tag(tag, *items, namespace=namespace, optional=optional)
    add_to_block_tag(tag, *items, namespace=namespace, optional=optional)


def copy_asset(path, template, out_file, variables):
    copy_file('%s/%s/%s' % (ASSETS_TEMPLATE_DIR, path, template), '%s/%s/%s' % (ASSETS_DIR, path, out_file), variables)


def copy_blockstate(template, out_file, variables):
    copy_asset('blockstates', template, out_file, variables)


def copy_block_model(template, out_file, variables, block_model_suffixes=['']):
    for suffix in block_model_suffixes:
        copy_asset('models/block', template + suffix, out_file + suffix, variables)


def copy_item_model(template, out_file, variables):
    copy_asset('models/item', template, out_file, variables)


def copy_blockstate_and_models(template, out_file, variables, block_model_suffixes=[''], item_model_suffix=''):
    copy_blockstate(template, out_file, variables)
    copy_block_model(template, out_file, variables, block_model_suffixes=block_model_suffixes)

    if item_model_suffix == '' and '_inventory' in block_model_suffixes:
        item_model_suffix = '_inventory'

    variables = variables.copy()
    if 'block' in variables:
        variables['block'] += item_model_suffix

    copy_item_model('block', out_file, variables)


def copy_data(path, template, out_file, variables):
    copy_file('%s/%s/%s' % (DATA_TEMPLATE_DIR, path, template), '%s/%s/%s' % (DATA_DIR, path, out_file), variables)


def copy_loot_table(template, out_file, variables):
    copy_data('loot_tables/blocks', template, out_file, variables)


def drop_itself(out_file, variables):
    copy_loot_table('drop_itself', out_file, variables)


def copy_recipe(template, out_file, variables):
    copy_data('recipes', template, out_file, variables)


def copy_crafting_recipe(template, out_file, variables):
    copy_recipe(template, 'crafting/' + out_file, variables)


def copy_block_tag(template, out_file, variables):
    copy_data('tags', template, 'blocks/' + out_file, variables)


def copy_item_tag(template, out_file, variables):
    copy_data('tags', template, 'items/' + out_file, variables)