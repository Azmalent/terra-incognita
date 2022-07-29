import os, subprocess
from glob import glob

this_dir = os.path.dirname(os.path.realpath(__file__))

for path in glob(this_dir + '/src/main/resources/assets/**/*.png', recursive=True):
    subprocess.run(['optipng', path])