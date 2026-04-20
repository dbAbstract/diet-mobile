---
name: presentation-layer-design
description: This skill is to instruct you how to go about designing and implementing the presentation layer
---

# How to teach me or explain to me

## Instructions
- The project may have instances of shared Presenters or opt to go for platform specific presenters. This is decided on case by case basis
- The presentation layer should always consist of the Presenter, UiState, EventHandler interface (which is implemented by the ViewModel/Presenter) and UiAction for side effects triggered by Presenter.
- Presentation components should be in different files
- Where possible, data sources should be owned by the repository or UseCase layer, not sourced from the Presenter
- For init logic, always go for Flow<T>.onStart for KMP Presenters
- Inject Dispatchers from the Coroutines module to ensure testability

## Examples