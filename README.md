# Item Finder

A Fabric mod for Minecraft that helps you find any item hidden in your chests — instantly.

Press a key, search for an item by name, click on it, and the mod tells you exactly which containers have it, guides you there with a particle trail and a world highlight, and lets you know the moment you've picked it up.

![Minecraft](https://img.shields.io/badge/Minecraft-26.2-brightgreen)
![Fabric](https://img.shields.io/badge/Mod%20Loader-Fabric-blue)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

---

## ✨ Features

- **Instant search UI** — press a key to open a search screen. Start typing and every matching item in the game (vanilla or modded) appears in a live filtered list.
- **Real container scanning** — uses client-server networking to scan actual chest and shulker box contents nearby, including containers you haven't opened yet.
- **Chat results** — get a clean list of every container that has your item, along with exact `X Y Z` coordinates.
- **World highlight** — matching chests are outlined in green so you can spot them at a glance.
- **Particle trail** — a trail of particles points you toward the nearest match.
- **On-screen HUD** — see the list of found containers and their distance from you at all times during a search.
- **Auto-complete found detection** — the moment you pick up the item you were looking for, the mod detects it, sends a confirmation message, and automatically stops the search (clearing the trail, highlight, and HUD).

## 📦 Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 26.2.
2. Download and install [Fabric API](https://modrinth.com/mod/fabric-api) for 26.2.
3. Download the latest `itemfinder-*.jar` from the [Releases](../../releases) page (or from the Actions build artifacts).
4. Drop the `.jar` file into your `.minecraft/mods` folder.
5. Launch the game with the Fabric profile.

## 🎮 How to Use

1. Press **O** (default keybind — rebindable in *Options → Controls*) to open the Item Finder screen.
2. Type the name of the item you're looking for.
3. Click the item from the list.
4. The screen closes automatically and the mod scans nearby containers.
5. Check the chat for coordinates, follow the particle trail or the green highlight to reach the chest.
6. Pick up the item — you'll get a **"You found it!"** message and the search will end automatically.

## 🛠️ Built With

- [Fabric Loader](https://fabricmc.net/) & [Fabric API](https://fabricmc.net/) for Minecraft 26.2
- Mojang's official (unobfuscated) mappings
- Custom client ↔ server networking (`CustomPacketPayload`) for accurate container scanning

## 🚧 Roadmap / Ideas

- [ ] Item icons in the search list (currently text-based)
- [ ] Highlight the exact slot inside a chest once opened
- [ ] Through-wall (X-ray style) highlighting
- [ ] Support for more container types (barrels, hoppers, minecart chests, ender chests)

## 📄 License

This project is licensed under the MIT License — feel free to use, modify, and share.

## 🙏 Acknowledgements

Built while learning Fabric modding for Minecraft 26.2 — a brand-new, actively evolving version of the API. Thanks to the Fabric documentation and community for the porting guides that made this possible.

The container-scanning approach (client-server networking for accurate chest contents) was inspired by [lann's Item Finder mod](https://github.com/fadhlanputra/itemfinder) (MIT License).
