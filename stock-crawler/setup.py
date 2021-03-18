from setuptools import setup, find_packages

setup(
    name='stock-crawler',
    version='1.0.1',
    packages=find_packages(),
    include_package_data=True,
    python_requires='>=3.8',
    install_requires=[
        'requests',
        'pandas',
        'beautifulsoup4',
        'pytest'
    ]
)

