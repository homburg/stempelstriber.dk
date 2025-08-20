# 1. Move from master to main

- Status: In Progress
- Deciders: Thomas H.
- Date: 2025-08-20

## Abstract

This RFC proposes to rename the `main` branch to `main` in this repository.

## Motivation

The term "master" is outdated and has connotations that are not inclusive. The tech community is moving towards using "main" as the default branch name. This change will align this repository with the industry best practice.

## Plan

1.  Create a new `main` branch from `main`.
2.  Push the `main` branch to the remote repository.
3.  Change the default branch on GitHub to `main`.
4.  Update all references to `main` in the codebase to `main`.
5.  Delete the `main` branch.
