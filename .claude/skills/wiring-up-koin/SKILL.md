---
name: wiring-up-koin
description: This skill is to instruct you how to go about setting up the wiring of Koin
---

# How to teach me or explain to me

## Instructions

- Among the shared KMP modules, there are 2 variants. `:lib` and `:feature`.
- `:lib` modules (eg. `:lib:auth`) is for core functionality or I/O of a certain data.
- `:lib` modules will always consist of two child modules. `:api` and `:impl`
- `:feature` modules (eg. `:feature:history`) would be for consumer facing functionalities
- `:feature` modules will always consist of three child modules. `:ui`, `:data` and `:domain`
- `:feature:someFeature:ui` will, however, live in the native codebases since that's where it's
  implemented
- The feature's data and ui child modules, will depend on domain
- Each parent module will have an aggregator DI Koin module, every child module will have its own
  public Koin module
- which then gets included in the parent module's Koin module.
- The parent's Koin module is then added to the :lib:di module's Koin module.

## Examples