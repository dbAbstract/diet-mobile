#!/bin/sh
set -e

echo "Setting up Yaseyo development environment..."

if ! command -v lefthook >/dev/null 2>&1; then
  echo "Lefthook not found, installing via Homebrew..."
  brew install lefthook
fi

lefthook install
echo "Git hooks installed."

echo ""
echo "Setup complete. Don't forget to add your Firebase config files:"
echo "  Android -> composeApp/google-services.json"
echo "  iOS     -> iosApp/iosApp/GoogleService-Info.plist"
