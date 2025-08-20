# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a static website for the webcomic "Havhesten" (stempelstriber.dk), serving pre-rendered HTML files with responsive design for both desktop and mobile viewing. The site supports both Danish and English languages.

## Architecture

### Static Site Structure
- **No build system**: This is a pure static HTML/CSS/JS site with no package.json, build scripts, or compilation steps
- **Pre-rendered pages**: Each comic page exists as individual HTML files in `c/da/` (Danish) and `c/en/` (English) directories
- **Responsive images**: Different image resolutions served from `image.stempelstriber.dk/` based on screen size and device pixel ratio
- **Client-side navigation**: JavaScript handles image lightbox functionality and responsive display switching

### Directory Structure
- `index.html`: Landing page (first comic strip)
- `c/da/[1-13].html`: Danish comic pages
- `c/en/[1-13].html`: English comic pages  
- `assets/`: JavaScript libraries and navigation images
- `image.stempelstriber.dk/`: Comic images organized by resolution (127x, 700x, 844x)
- `cdn.c.homburg.dk/`: Additional image assets
- `docs/rfcs/`: Project RFCs and documentation

### Frontend Technology
- Vanilla HTML/CSS/JS with no frameworks
- CSS Flexbox for responsive layout
- Media queries for mobile/desktop switching
- ImageLightbox library for image viewing
- Inline CSS and JavaScript compilation via frontend build tools

## Development Workflow

### No Build Commands
This project has no build, test, or lint commands as it's a static site. Changes are made directly to HTML/CSS/JS files.

### Adding New Comic Pages
1. Create new HTML file in both `c/da/` and `c/en/` directories
2. Follow existing naming pattern (incrementing numbers)
3. Update navigation links in adjacent pages
4. Add corresponding images to image directories

### RFC Process
When working with RFCs in `docs/rfcs/`:
- Update RFC status in dedicated commits
- Update the RFC list in `docs/rfcs/README.md` when adding new RFCs or changing status
- Follow the established RFC format and numbering

### Image Organization
Images are organized by resolution and language:
- 844x resolution for desktop (dansk/, engelsk/ subdirectories)
- 700x resolution for mobile (mobil/, "mobil - engelsk/" subdirectories)  
- 127x resolution for thumbnails/tests (tests/ subdirectory)

## GitButler Integration

This project can be integrated with GitButler for automated branch management during Claude Code sessions.

### Benefits
- Automatically isolate code changes into virtual or stacked branches
- Context-aware commit messages
- No need to manually manage git commits during development

### Setup
1. Install GitButler CLI (via GitButler General settings)
2. Configure hooks in Claude settings file (`~/.claude/settings.json`):

```json
{
  "hooks": {
    "PreToolUse": [{ "matcher": "Edit|MultiEdit|Write", "hooks": [{ "type": "command", "command": "but claude pre-tool" }] }],
    "PostToolUse": [{ "matcher": "Edit|MultiEdit|Write", "hooks": [{ "type": "command", "command": "but claude post-tool" }] }],
    "Stop": [{ "matcher": "", "hooks": [{ "type": "command", "command": "but claude stop" }] }]
  }
}
```

### Workflow Integration
- Never use the `git commit` command after a task is finished
- GitButler automatically manages branching and commits during Claude Code sessions
- Each Claude session creates separate branches for isolation

## Key Files to Understand

- `index.html`: Contains complete CSS styling and responsive design patterns used throughout the site
- `assets/frontend-*.js`: Contains JavaScript functionality for navigation and image handling
- `GEMINI.md`: Contains project overview and Gemini-specific instructions about RFC management