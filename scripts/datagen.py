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
    assert(os.path.isfile(template))

    out_dir = os.path.dirname(out_file)
    if not os.path.exists(out_dir):
        try:
            os.path.makedirs(out_dir)
        except OSError as error:
            if error.errno != errno.EEXIST:
                raise

    with open(template, 'rt') as fin:
        with open(out_file, 'wt') as fout:
            for line in fin:
                string = line.replace('$MODID', MODID)
                for k in vars:
                    string = string.replace('$' + k.upper(), vars[k])
                fout.write(replacevars(string, vars))


def copy_asset(path, template, out_file, vars):
    copy_file('%s/%s/%s' % (ASSETS_TEMPLATE_DIR, path, template), '%s/%s/%s' % (ASSETS_DIR, path, out_file))


def copy_blockstate(template, out_file, vars):
    copy_asset('blockstates', template, out_file, vars)


def copy_block_model(template, out_file, vars):
    copy_asset('models/block', template, out_file, vars)


def copy_item_model(template, out_file, vars):
    copy_asset('models/item', template, out_file, vars)


def copy_data(path, template, out_file, vars):
    copy_file('%s/%s/%s' % (DATA_TEMPLATE_DIR, path, template), '%s/%s/%s' % (DATA_DIR, path, out_file))


def copy_loot_table(template, out_file, vars):
    copy_data('loot_tables/blocks', template, out_file, vars)


def copy_recipe(template, out_file, vars):
    copy_data('recipes', template, out_file, vars)


def make_simple_cube(blockname):
    vars = {'block': blockname}
    copy_blockstate('simple_blockstate', blockname, vars)
    copy_block_model('cube', blockname, vars)
    copy_item_model('block', blockname, vars)


def make_stairs_and_slabs(type, texture_source, condition):
    block = type + '_stairs'
    vars = {'type': type, 'block': block, 'texture_source': texture_source, 'condition': condition}
    copy_blockstate('stairs', block, vars)
    for model_type in ['', '_inner', '_outer']:
        copy_block_model('stairs' + model_type, block + model_type, vars)
    copy_item_model('block', block, vars)
    copy_loot_table('drop_itself', block, vars)
    copy_recipe('stairs', '%s/%s' % (type, block), vars)

    block = type + '_slab'
    vars['block'] = block
    copy_blockstate('slab', block, vars)
    copy_block_model('slab', block, vars)
    copy_block_model('slab_top', block + '_top', vars)
    copy_item_model('block', block, vars)
    copy_loot_table('drop_itself', block, vars)
    copy_recipe('stairs', '%s/%s' % (type, block), vars)

    block = type + '_vertical_slab'
    vars['block'] = block
    copy_blockstate('vertical_slab', block, vars)
    copy_block_model('vertical_slab', block, vars)
    copy_item_model('block', block, vars)
    copy_loot_table('drop_itself', block, vars)
    copy_recipe('stairs', '%s/%s' % (type, block), vars)