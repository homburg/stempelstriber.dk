# Stempelstriber.dk Website

## Directory Overview

This directory contains the source code for the stempelstriber.dk website, a static website that displays the webcomic "Havhesten". The website is available in both Danish and English.

The directory is structured to serve pre-rendered HTML files for each comic page, with different image sizes optimized for various screen resolutions.

## Key Files

*   `index.html`: The landing page of the website, which displays the first comic strip.
*   `c/da/`: This directory contains the Danish version of the comic strips, with each HTML file representing a single page.
*   `c/en/`: This directory contains the English version of the comic strips, with each HTML file representing a single page.
*   `assets/`: This directory contains the JavaScript and CSS files used by the website, as well as images for the navigation and language selection.
*   `image.stempelstriber.dk/`: This directory contains the comic strip images, which are organized by resolution and language.
*   `cdn.c.homburg.dk/`: This directory contains additional images used on the website.

## Usage

The website is a static site and can be viewed by opening the `index.html` file in a web browser. The comic can be navigated by clicking the arrow buttons on the left and right sides of the page. The language can be switched by clicking the flag icons in the top right corner.

## Gemini Instructions

- When a new RFC is added or an existing RFC's status is updated, please update the RFC list in `docs/rfcs/README.md`.