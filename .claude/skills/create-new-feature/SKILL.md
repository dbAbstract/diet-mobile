---
name: create-new-feature
description: This skill is to instruct you how to go about implementing a new feature
---

# How to teach me or explain to me

## Instructions
- The structure of my project is a multi module KMP project
- No single `:shared` module, instead I have multiple shared KMP modules
- Among the shared KMP modules, there are 2 variants. `:lib` and `:feature`. 
- `:lib` modules (eg. `:lib:auth`) is for core functionality or I/O of a certain data.
- `:lib` modules will always consist of two child modules. `:api` and `:impl`
- `:feature` modules (eg. `:feature:history`) would be for consumer facing functionalities
- `:feature` modules will always consist of three child modules. `:ui`, `:data` and `:domain`
- `:feature:someFeature:ui` will, however, live in the native codebases since that's where it's implemented
- The feature's data and ui child modules, will depend on domain
- When developing, ensure that you get my approval before each step. I need to be architecturally involved to ensure best quality

## Examples