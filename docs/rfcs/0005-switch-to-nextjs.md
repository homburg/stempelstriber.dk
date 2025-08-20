# 5. Switch to Next.js site

- Status: Proposed
- Deciders: Thomas H.
- Date: 2025-08-20

## Abstract

This RFC proposes migrating the stempelstriber.dk website from a static HTML/CSS/JS site to a Next.js application to improve maintainability, development experience, and enable modern web features.

## Motivation

The current static site architecture has limitations:
- Manual HTML file management for each comic page
- Duplicate code across Danish/English versions
- No build-time optimizations
- Limited SEO capabilities
- Difficulty adding dynamic features
- Manual image optimization

A Next.js migration would provide:
- Automated page generation from data
- Internationalization (i18n) support
- Image optimization
- Better SEO with metadata management
- Modern development workflow
- Component-based architecture
- TypeScript support

## Current Architecture Analysis

### Static Site Structure
- 13 comic pages × 2 languages = 26 HTML files
- Inline CSS/JS repeated across files
- Manual navigation link updates
- Images served from external domains
- No build system or bundling

### Pain Points
- Adding new comics requires updating multiple files
- Code duplication between language versions
- No automatic image optimization
- Limited metadata management
- Manual dependency management

## Proposed Next.js Architecture

### Core Features
- **Dynamic routing**: `/c/[lang]/[page]` for comic pages
- **Internationalization**: Built-in i18n for Danish/English
- **Image optimization**: Next.js `<Image>` component with responsive loading
- **Static generation**: Pre-render all comic pages at build time
- **Component system**: Reusable components for navigation, comic viewer, language switcher

### Directory Structure
```
src/
  pages/
    index.js              # Landing page
    c/
      [lang]/
        [page].js         # Dynamic comic pages
  components/
    ComicViewer.js        # Comic display component
    Navigation.js         # Page navigation
    LanguageSwitcher.js   # Language toggle
  data/
    comics.json           # Comic metadata
  public/
    images/               # Optimized comic images
```

### Data Management
- JSON-based comic metadata
- Automated page generation from data
- Single source of truth for comic information

## Migration Plan

### Phase 1: Setup and Core Pages
1. Initialize Next.js project
2. Set up i18n configuration
3. Create basic page structure
4. Implement comic data structure

### Phase 2: Component Development
1. Build ComicViewer component
2. Implement responsive navigation
3. Create language switching functionality
4. Add lightbox functionality

### Phase 3: Content Migration
1. Extract comic metadata to JSON
2. Migrate images to Next.js public directory
3. Implement image optimization
4. Test all comic pages

### Phase 4: Enhancement and Deployment
1. Add SEO metadata
2. Implement performance optimizations
3. Set up deployment pipeline
4. Migrate domain and hosting

## Benefits

### Developer Experience
- Modern development tools and workflow
- Hot reloading during development
- TypeScript support for better code quality
- Component-based architecture for maintainability

### Performance
- Automatic code splitting
- Image optimization and lazy loading
- Static generation for fast loading
- Modern bundling and minification

### Maintainability
- Single source of truth for comic data
- Automated page generation
- Consistent styling through CSS modules/styled components
- Easier content updates

### SEO and Accessibility
- Better metadata management
- Structured data support
- Improved accessibility features
- Search engine optimization

## Considerations

### Migration Effort
- Significant initial development time
- Learning curve for Next.js if unfamiliar
- Need to migrate existing content and styling

### Hosting Changes
- Requires Node.js hosting or static export
- Potential deployment pipeline changes
- May need CDN reconfiguration

### Backwards Compatibility
- Ensure existing URLs continue to work
- Maintain responsive design principles
- Preserve current functionality

## Success Criteria

1. All 13 comic pages display correctly in both languages
2. Responsive design maintained across devices
3. Navigation and language switching work as expected
4. Performance improvements measurable
5. Development workflow simplified for adding new comics

## Dependencies

This RFC may impact:
- RFC 2: Document automated workflows (may need updated build documentation)
- Current hosting and deployment setup
- Image hosting strategy