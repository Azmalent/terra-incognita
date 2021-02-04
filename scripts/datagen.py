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


def make_basic_block(name, default_loot_table=True):
    vars = {'block': name}
    copy_blockstate('simple_blockstate', name, vars)
    copy_block_model('cube', name, vars)
    copy_item_model('block', name, vars)
    if default_loot_table:
        drop_itself(name, vars)


def make_pottable_plant(name):
    vars = {'block': name}
    copy_blockstate('simple_blockstate', name, vars)
    copy_block_model('bush', name, vars)
    copy_item_model('flat_block', name, vars)
    drop_itself(name, vars)

    potted_name = vars['block'] = 'potted_' + name
    copy_blockstate('simple_blockstate', potted_name, vars)
    copy_block_model('potted_bush', potted_name, vars)
    copy_loot_table('potted_bush', potted_name, vars)


def make_flower(name, dye_color, condition):
    make_pottable_plant(name)
    copy_crafting_recipe('shapeless_one_ingredient', 'dyes/dye_from_' + name, {
        'ingredient': name,
        'color': 'minecraft:%s_dye' % dye_color,
        'count': '1',
        'condition': condition
    })


def make_stairs_and_slabs(type, base_block, condition):
    block = type + '_stairs'
    vars = {'type': type, 'block': block, 'base_block': base_block, 'condition': condition}
    copy_blockstate_and_models('stairs', block, vars, block_model_suffixes=['', '_inner', '_outer'])
    drop_itself(block, vars)
    copy_crafting_recipe('stairs', type + '/stairs', vars)

    block = type + '_slab'
    vars['block'] = block
    copy_blockstate_and_models('slab', block, vars, block_model_suffixes=['', '_top'])
    drop_itself(block, vars)
    copy_crafting_recipe('slabs', type + '/slab', vars)

    block = type + '_vertical_slab'
    vars['block'] = block
    copy_blockstate_and_models('vertical_slab', block, vars)
    drop_itself(block, vars)
    copy_crafting_recipe('vertical_slabs', 'compat/quark/%s/vertical_slabs' % type, vars)


def make_button(type, base_block, condition):
    block = type + '_button'
    vars = {'type': type, 'block': block, 'base_block': base_block, 'ingredient': base_block, 'output': block, 'count': '1', 'condition': condition}
    copy_blockstate_and_models('button', block, vars, block_model_suffixes=['', '_inventory', '_pressed'])
    drop_itself(block, vars)
    copy_crafting_recipe('shapeless_one_ingredient', type + '/button', vars)


def make_pressure_plate(type, base_block, condition):
    block = type + '_pressure_plate'
    vars = {'type': type, 'block': block, 'base_block': base_block, 'condition': condition}
    copy_blockstate_and_models('pressure_plate', block, vars, block_model_suffixes=['', '_down'])
    drop_itself(block, vars)
    copy_crafting_recipe('pressure_plate', type + '/pressure_plate', vars)


def make_wood_type(type):
    # Sapling and leaves
    sapling = type + '_sapling'
    make_pottable_plant(sapling)

    leaves = type + '_leaves'
    make_basic_block(leaves, default_loot_table=False)
    copy_loot_table('leaves', leaves, {'block': leaves, 'sapling': sapling})

    leaf_carpet = type + '_leaf_carpet'
    vars = {'type': type, 'block': leaf_carpet}
    copy_blockstate('simple_blockstate', leaf_carpet, vars)
    copy_block_model('leaf_carpet', leaf_carpet, vars)
    copy_item_model('block', leaf_carpet, vars)
    drop_itself(leaf_carpet, vars)
    copy_crafting_recipe('leaf_carpet', 'compat/quark/%s/leaf_carpet' % type, vars)

    # Logs and wood
    for prefix in ['', 'stripped_']:
        log = prefix + type + '_log'
        vars = {'type': prefix + type, 'block': log}
        copy_blockstate_and_models('log', log, vars, block_model_suffixes=['', '_horizontal'])
        drop_itself(log, vars)

        wood = vars['block'] = prefix + type + '_wood'
        copy_blockstate_and_models('wood', wood, vars)
        drop_itself(wood, vars)

        post = prefix + type + '_post'
        vars = {'type': prefix + type, 'block': post, 'ingredient': wood}
        copy_blockstate_and_models('post', post, vars)
        drop_itself(post, vars)
        copy_crafting_recipe('post', 'compat/quark/%s/%spost' % (type, prefix), vars)


    copy_block_tag('wood', type + '_wood' , vars)
    copy_item_tag('wood', type + '_wood', vars)

    # Planks, vertical planks, stairs and slabs
    planks = type + '_planks'
    make_basic_block(planks)
    make_basic_block('vertical_' + planks)
    make_stairs_and_slabs(type, planks, type)

    copy_crafting_recipe('planks', type + '/planks', {'type': type})
    copy_crafting_recipe('vertical_planks', 'compat/quark/%s/vertical_planks' % type, {'type': type})

    # Buttons and pressure plates
    make_button(type, planks, type)
    make_pressure_plate(type, type + '_planks', type)

    # Fences and hedges
    fence = type + '_fence'
    vars = {'type': type, 'block': fence, 'base_block': planks}
    copy_blockstate_and_models('fence', fence, vars, block_model_suffixes=['_inventory', '_post', '_side'])
    drop_itself(fence, vars)
    copy_crafting_recipe('fence', type + '/fence', vars)

    fence_gate = vars['block'] = fence + '_gate'
    copy_blockstate_and_models('fence_gate', fence_gate, vars, block_model_suffixes=['', '_open', '_wall', '_wall_open'])
    drop_itself(fence_gate, vars)
    copy_crafting_recipe('fence_gate', type + '/fence_gate', vars)

    hedge = vars['block'] = type + '_hedge'
    copy_blockstate_and_models('hedge', hedge, vars, block_model_suffixes=['_extend', '_post', '_side'], item_model_suffix='_post')
    drop_itself(hedge, vars)
    copy_crafting_recipe('hedge', 'compat/quark/%s/hedge' % type, vars)

    # Doors and trapdoors
    door = vars['block'] = type + '_door'
    copy_blockstate('door', door, vars)
    copy_block_model('door', door, vars, block_model_suffixes=['_bottom', '_bottom_hinge', '_top', '_top_hinge'])
    copy_item_model('item', door, {'item': door})
    drop_itself(door, vars)
    copy_crafting_recipe('door', type + '/door', vars)

    trapdoor = vars['block'] = type + '_trapdoor'
    copy_blockstate_and_models('trapdoor', trapdoor, vars, block_model_suffixes=['_bottom', '_open', '_top'], item_model_suffix='_bottom')
    drop_itself(trapdoor, vars)
    copy_crafting_recipe('trapdoor', type + '/trapdoor', vars)

    # Other stuff
    bookshelf = vars['block'] = type + '_bookshelf'
    copy_blockstate('simple_blockstate', bookshelf, vars)
    copy_block_model('bookshelf', bookshelf, vars)
    copy_item_model('block', bookshelf, vars)
    copy_loot_table('bookshelf', bookshelf, vars)
    copy_crafting_recipe('bookshelf', 'compat/quark/%s/bookshelf' % type, vars)

    ladder = vars['block'] = type + '_ladder'
    copy_block_model('rotatable', ladder, vars)
    copy_block_model('ladder', ladder, vars)
    copy_item_model('flat_block', ladder, vars)
    drop_itself(ladder, vars)
    copy_crafting_recipe('bookshelf', 'compat/quark/%s/bookshelf' % type, vars)

    # TODO: boats, signs