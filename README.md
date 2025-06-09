# Oblivion
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/oblivion-api?style=flat&logo=modrinth&label=Modrinth)](https://modrinth.com/mod/oblivion-api)
[![CurseForge Downloads](https://img.shields.io/curseforge/dt/1096331?style=flat&logo=curseforge&label=Curseforge)](https://www.curseforge.com/minecraft/mc-mods/oblivion-api)
![Modrinth Game Versions](https://cf.way2muchnoise.eu/versions/oblivion-api.svg)

Fabric requires Fabric API, Porting Lib, and Forge Config API Port.

A Minecraft modding library. After implementation (here, once), cast the details into *Oblivion*, and call simple, intuitive methods.

### Features
- Abstract Mixin Plugin for easy mod-dependent mixins.
- Some extension methods for Block-, Item-, or FluidTypeProperties to undo calls like `noCollission()` \[sic\] (atm only a subset of properties is available).
- Advancement Brute Forcer to grant advancements by name, regardless of the actual criteria defined.
- Easy System to provide additional resource/data packs.
- Simple Registration of Potion Recipes, Compostability, and Furnace Fuel Time.
- Mod-agnostic Sign Registration.
- Mod-agnostic Boat Registration.
- IShownInCreativeTab interface definition.

### F.A.Q.
**Q: Can this be used in my modpack?**  
A: Yes of course, so long as when the pack file (zip/mrpack/any other format) is opened (without using a launcher to install it), the mod file (jar) isn't found inside. 

**Q: Newer versions?**  
A: Planned, eventually.

### Licensing
This project is released under the [Avoiding Leeching License, version 1.1](LICENSE).  

[HangingSignBlockEntityMixin.java](common/src/main/java/com/github/voidleech/oblivion/mixin/HangingSignBlockEntityMixin.java) is alternatively released under the [MIT License](other-licenses/mit-self): it ensures that the `type` parameter in the HangingSignBlockEntity superclass constructors is set to the correct type (ugh vanilla why hardcode it), which is relevant if someone mixes into there and uses the parameter, and is compatible with other mixins like it, so feel free to use.

#### Third-Party Content
The boat code in this project is based on and contains code from Kaupenjoe's 1.20.X Forge Tutorial, which is licensed under the [MIT license](other-licenses/mit-kaupenjoe).  
The multiloader-compatible registration code in this project is based on and contains code from Lilypuree's Decorative Blocks, which is licensed under the [MIT license](other-licenses/mit-lilypuree).  
The code for enabled-by-default resource packs on the Forge side, and for additional valid blocks on the sign BET on the Fabric side, is based on FabricMC's Fabric, which is licensed under the [Apache 2.0 license](other-licenses/apache-fabric).  