import subprocess
from glob import glob
from os import path

for filepath in glob(path.abspath('../src/main/resources/assets/**/*.png'), recursive=True):
    subprocess.run(['optipng', '-quiet', filepath])