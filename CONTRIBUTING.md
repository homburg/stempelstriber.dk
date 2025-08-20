# Contributing to Stempelstriber.dk

Thank you for your interest in contributing to the Havhesten webcomic website!

## Project Overview

This is a static website for the webcomic "Havhesten" (stempelstriber.dk), serving pre-rendered HTML files with responsive design for both desktop and mobile viewing. The site supports both Danish and English languages.

## Project Purpose

The website displays webcomic strips in a clean, responsive interface that works across different devices and screen sizes. It serves static content with no build system or server-side processing required.

## Development Setup

### Prerequisites
- A modern web browser for testing
- A text editor for making changes
- Git for version control

### Local Development
1. Clone the repository
2. Open `index.html` in your web browser to view the site locally
3. Make changes directly to HTML, CSS, and JavaScript files
4. Refresh the browser to see changes

### File Structure
- `index.html`: Landing page (first comic strip)
- `c/da/[1-13].html`: Danish comic pages
- `c/en/[1-13].html`: English comic pages
- `assets/`: JavaScript libraries and navigation images
- `image.stempelstriber.dk/`: Comic images by resolution
- `docs/rfcs/`: Project RFCs and documentation

## Code Style Guidelines

### HTML
- Use semantic HTML elements
- Maintain consistent indentation (2 spaces)
- Include proper `alt` attributes for images
- Follow existing responsive design patterns

### CSS
- Follow existing CSS conventions in `index.html`
- Use CSS Flexbox for layouts
- Maintain responsive design with media queries
- Keep mobile-first approach

### JavaScript
- Use vanilla JavaScript (no frameworks)
- Follow existing patterns for image handling and navigation
- Maintain compatibility with existing ImageLightbox library

## Contribution Process

### For Bug Fixes and Small Changes
1. Fork the repository
2. Make your changes
3. Test changes across different screen sizes
4. Submit a pull request with clear description

### For New Features
1. Check existing RFCs in `docs/rfcs/` 
2. Consider creating an RFC for major changes
3. Discuss the change in an issue first
4. Follow the same fork/PR process

### Adding New Comic Pages
1. Create HTML files in both `c/da/` and `c/en/` directories
2. Follow existing naming pattern (incrementing numbers)
3. Update navigation links in adjacent pages
4. Add corresponding images to appropriate directories
5. Test responsive behavior on mobile and desktop

## Additional Documentation

More specific workflow documentation and tooling guidelines will be added as the project evolves. Check the RFCs for planned improvements and processes.