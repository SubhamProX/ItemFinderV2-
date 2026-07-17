# Item Finder Mod — Progress Tracker

Package name: `subham.itemfinder`

## Folder Structure

```
itemfinder_mod/
├── build.gradle
├── gradle.properties
└── src/main/
    ├── java/subham/itemfinder/
    │   ├── ItemFinderClient.java   <- entrypoint + keybind (DONE)
    │   ├── ItemFinderGui.java      <- simple "Hello" label GUI (DONE)
    │   └── ItemFinderScreen.java   <- screen wrapper (DONE)
    └── resources/
        └── fabric.mod.json
```

## Progress (Humare Step-by-Step Plan Ke Hisaab Se)

- [x] **Step 1-3**: Entrypoint + keybind register + test (`ItemFinderClient.java`)
- [x] **Step 4**: Sabse simple GUI — sirf "Hello" label (`ItemFinderGui.java` + `ItemFinderScreen.java`)
- [ ] **Step 5**: Search box add karo (`WTextField`) — abhi filter logic ke bina
- [ ] **Step 6**: Filter logic add karo — console mein print karke test karo
- [ ] **Step 7**: Item grid visually render karo (`WItem` widgets)
- [ ] **Step 8**: "Click to scan" functionality add karo

## Ab Kya Karna Hai

1. Ye poora `itemfinder_mod` folder apne Fabric Example Mod project mein copy karo
   (ya isko hi apna project root bana lo agar `settings.gradle` bhi add kar do)
2. JVDroid mein package name check karo — sab jagah `subham.itemfinder` match hona chahiye
3. Termux se `./gradlew build` chalao
4. Game mein "O" key dabao — ek khaali panel ke andar "Hello" text dikhna chahiye

Agar build error aaye, error message yahan wapas paste kar dena.

## Note: `settings.gradle` Missing Hai

Ye file is folder mein nahi hai kyunki ye Fabric Example Mod ke saath already aati hai —
usko replace mat karna, bas uske `build.gradle`, `gradle.properties`, aur `src/` folder ko
is folder ke content se replace/merge karna.
