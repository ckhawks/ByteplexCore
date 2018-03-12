## BytePlex: Corporation Conquest

------

Entries noted with a (?) are highly likely to change



### The Overview

We are looking to create a Minecraft server that focuses on group progression through a unique gang system. The map will be divided into capturable points of interest, or nodes, that can yield special bonuses or resources to the owning gang. Gang cooperation will be required to survive due to the harsh "fight-or-be-killed" atmosphere.

#### The History and Inspirations

Main features of a prison server:

- Multitude of ranks that you grind through, exponentially gets harder to progress
- Goal is to become top dog
- Each level has its own resource areas
- Guards are there to stop PVP / illegal activity

Notable problems with current prison servers:

- They are money hungry. Donations and advertising are shoved in your face, alongside breaking EULA with quite a lot with monetization strategies - everyone who pays is at the top (pay2win as heck).
- They are grindy in a boring way. Ranking up is exponentially more and more grindy, and there's not really any new content, just different blocks that you see. You do the same thing as you go up, and it doesn't get more difficult at all.
- Typically, to give more stuff to do after you're "free", servers will just add more ranks ("rank-spam")
- PVP is an afterthought. It is limited to specific zones, and high-tier players dominate these zones.
- Moderation is very strict and authoritarian
- Nothing really feels like a prison



> "The main draw seems to be the potential to become the ‘big dog’ on the server and to achieve high status."

We can keep this as our "goal" of sorts.



#### What are we doing?

Each level still has its' own resource areas, but they are presented in a much more interesting manner. Player versus player combat is actually a primary characteristic and not an afterthought; danger is always in the back of your mind on the map as there are very little safe areas (No PVP arenas). We still have the goal of being the top dog of the server and to achieve high status, but the high status players are actually playing directly in competition with one another instead of playing against an infinitely generating mine. The grindiness is reduced due to the fact that progression is group based instead of individual based. You work together with your gang mates in order to accomplish your objectives. 





------



### Planned Features

#### Gameplay

- Normal Minecraft crafting will not be available, or will be severely limited. Instead, players will have to capture manufacturing-based nodes to craft certain items. 
  - Simple items such as wooden tools may be available to craft.
- Solo play will be discouraged - power in numbers will be encouraged, it will be hard to hold territory or survive fights alone
- There would not be a money system as an economy; instead, everything is done with pure resources.
- Resources
  - Collection
    - Collection of resources would occur at resource nodes. Gang members would get efficiency / speed bonuses for doing things together (if everyone is mining at one node, everyone goes faster).
    - Randomly placed resource pockets (think Rust style) could be used to collect small amounts of resources if the gang does not own any nodes.
  - Processing
    - Gangs will be able to bring unprocessed, raw resources to processing nodes in order to turn their materials into usable components.
    - Processing nodes would be for different purposes (i.e. each one has a specific purpose)
    - This encourages gangs to barter with other gangs, because they might only have nodes that focus around iron while they need food or something. Imports/exports system
  - Manufacturing
    - Gangs will be able to take refined materials to manufacturing (factory) nodes in order to create usable items such as tools, weapons, armor, and food.
    - Different manufacturing nodes would be used to produce different things, furthering the imports/exports system
  - Packaging / Transport
    - Shipments can be packaged and taken from each of the different nodes to move items. While a player holds a shipment of items, they will have a slower movement speed and will broadcast on the world map. This means that gang members must protect shipments forcing you to move as a convoy, and convoys can be ambushed in order to steal the shipments.
    - This also means that trade can occur, but double-crosses can happen too (and will be allowed!).
- Guilds (or gangs)
  - Players can join together and form a persistent guild.
  - These guilds hold territory in the form of nodes (also known as points of interest)
  - This territory can give bonuses, but typically provide a resource output
  - Territory can be upgradable (upgrade will finish in 16 hours type of thing) in order to produce higher output
  - Guilds have a resource inventory
  - (?) Gang security level/danger level would be determined by the average of their members. (?)
  - Guilds can level up to the next tier using a specific amount of certain resources
    - This cost would scale exponentially with the amount of people in the gang
    - Each node upgrade cost would also scale exponentially with amount of people in gang
  - Guilds can only have as many players as their headquarters supports (leveling up the headquarters node would increase max player limit for the gang)
  - Guilds can select decoration options to be used in territory they own
    - Primary and secondary detail blocks
    - Team banner (perhaps donator perk)
    - Team totem (perhaps donator perk)
  - Guilds would have a headquarters node (could also be called a Barracks)
    - Gangs would choose an empty node to found the guild at. They would be able to choose between 3 headquarters styles for their home node.
    - This node cannot be taken unless all other nodes of theirs are taken
    - Headquarters node would house the guilds' resources and would act as a home for the guild members
    - Headquarters node could have upgradable sections, such as adding a blacksmith that allows for some sort of production bonus
  - Guild nodes could theoretically have player-designable sections
    - Or maybe not, because we are doing that type of stuff in the actual battles
  - (?) Gang members can be designated by leader as a certain role (Miner, woodcutter, farmer, etc) - gives bonus to resource collection, but can only change designation every 1 day or something
- Nodes
  - Each node would require a resource upkeep in order to maintain ownership, otherwise they will decay away to neutral. 
  - Nodes would have a security level (or danger level)
    - This would be calculated based on node tier and bonus quality.
    - It would be dumb for a newer, lower tier gang to attempt to overtake a high tier node.
  - Battle system
    - When an attacking gang wants to overtake a point of interest, they can initiate a battle at the node. A timer (around 10:00) would start, allowing each team to queue on the queue pads near the node. Once each team has enough players queued (5ish for smaller nodes and 10+ for larger), or the timer runs out, the battle system would teleport players to a new server instance. This server instance would be dynamically created whenever the timer begins. This server instance would feature Competitive Minecraft-style (oc.tc or Stratus Network) style battles, where most of the map can be modified and such. The map used for the battle would be an expanded version of the point of interests' build. Once the battle ends, the outcome is communicated back to the main server, and the players are transported back. If the defenders lost the battle, the attackers take the node over, placing the node in a grace period (maybe 24 hours?). 	
    - Potential gamemodes:
      - CTF
      - TDM
      - Control the Transformers (shut down the power)
      - Payload
      - Domination (3 control points, 15 minute game or so)
    - Defenders and attackers queue for a battle by standing on their respective pad at the node.
      - Technical: Queue pads could have holograms above them
    - Defenders would be alerted when attackers initiate an attack on the node. Defenders would be given the option to teleport to the defenders' pad if they are within 100 blocks of the node center. (This is so that they are not prevented from queuing for the battle).
      - Defenders could get chat alerts, Discord alerts, and a title popup on their screen ("Get to the Nuclear Power Plant before it's too late!")
    - Attackers would only be able to outnumber the defenders by 1-2 people. Defenders would be allowed to bring as many people as the node can support. This can be iterated on based on community feedback.
    - Guilds would still be vulnerable for attack after they logout for a few minutes, in order to prevent "raid combat logging"
  - Passive node battle system
    - If there are no defenders available to defend a node (all offline, or some online but did not come to defend), attackers can start a passive takeover. This would look like the attackers contributing resources (maybe?) in order to lower the health of the node. Every few hours, the health of the node can be ticked down about 1/5 of the way, resulting in it taking about 24 hours to fully decay away a node. 
  - After a node changes ownership, it is placed in a grace period (24 hours?). This enables the node to produce something for the owners before it can be lost.
- Players
  - Players would have a reputation, which will be shown through their player nameplate. As they demonstrate bad behavior (what would be constituted as bad behavior?) their reputation will fall. This allows players to decide whether or not they should trust someone or a gang based upon their reputation. (Red = bad, Blue = good)
  - Reputation could be awarded by other players for successful deals, or slowing gaining reputation over time.
- Special Events
  - Special events (also known as Alerts) could occur. These would occur at Points of Interest, and gangs who complete the objective will receive some sort of bonus or unique item
  - Players would not be able to enchant items themselves
  - Unique items could theoretically be:
    - Enchanted items
    - Bow with less draw time
    - Node shield (protect a node from attack for x hours)
    - Node speed boost (increase the output of a node for x hours)
    - Bonus Resources
    - Random aesthetic item (free lootboxes / keys?)
- Minor features
  - Welcome title displayed on join



We are planning to start in a Beta-esque system (consisting of a map with a small amount of nodes and players, maybe 20 or so of each), in order to demonstrate MVPs and gather feedback from initiate player base. Marketing would consist mostly of word of mouth marketing. Once we've iterated a few times based on player feedback, we can reset gangs and nodes and end beta.

Beta testers could get a unique Beta Tester title or something as a token of appreciation. 

#### Integrations

Web - Track stats to display on front end (in no specific order)

- Time played
- What time is spent doing
- Kill count (as a gang and solo)
- Top deaths (gang and solo)
- Bans (as a method of transparency)
- Money
- Top Voters
- Mined the most blocks (which blocks/blocktypes - wooden, stone/ore, farm?)
- Donated the most to the server
- Gang listings, their members, perhaps like a little blurb they write? (ROBLOX group-esque)

Discord

- Notifications when your gangs' nodes are being attacked
- Notifications when you are about to lose a node to decay
- Notifications that when new alerts, or global events, are available



------



### World Design

The map size could be increased as more players and gangs are created. (This could be dynamically or manually done)



#### World Design Ideas

Each of these are subject to discussion

- (?) Player start in a prison, which is located in a city, which is located in a large-scale open world explorable (but not modifiable) map. (Prison high security, city medium security, world low security)
- (?) Map is divided into 3 tiers (low, medium, and high), separated by security/danger level. Gangs would begin in tier 1, and as they get more powerful they would be relocated to the higher tier zones.
  - Once your gang reaches progression requirements for gang, they are moved to next zone and are invulnerable so they can pick an empty node to move their Town hall / Headquarters to. 
  - Low danger tier could have high police activity, while high danger would have no police activity.
- (?) Map nodes are distributed randomly, and each node has a security level associated with it based on bonus quality, size, and location. 
- (?) 3 different server instances, each with a different map. Divides into the 3 tiers
- (?) Subway fast-travel system connecting parts of the map. Costs money (resources) to use. Can be occupied by a gang in order to shut down fast travel to that area.
  - Technical: plugin system that allows u to define nodes and how they connect
    - plugin can calculate cost to travel to node
    - plugin generate maps in item frames given nodes



#### Node Ideas

The goal is to have each node have a stylized name to go with it. A decent example of this, as seen from Fallout, is the "GunRunners" gun store.

- Headquarters - places that are for gang administration

- Resources - places that are for raw resource collection - upgrading a resource could result in increased resource output

  - Tree farm
  - Mine shaft

- Refinery - places that are for processing raw materials - upgrading a refinery could result in higher resource quality or processing efficiency

  - Ironworks (Iron making)
  - Steel mill (Steelmaking)
  - Gemcutter's Workshop

- Factory - places that are for manufacturing goods (typically in bulk) - upgrading a factory could give a bonus to resource efficiency

  - Diamond Sword Factory
  - Iron Armorsmith 
  - Brewery

- Shipping - places that are for exporting / importing goods (maybe this could be near the subway?) - upgrading a shipping facility could increase revenue, or increase storage capacity of shipments

  - Harbor
  - Train yard
  - Packaging Facility - reduces size of items in inventory, but when packages are held they decrease movement speed and broadcast location to entire server 

- Power Plant - places that are for providing power / energy to other nodes - upgrading power plants could increase net energy out to other nodes, or decrease the amount of fuel it takes to power

  - Nuclear Power Plant
  - Coal-fired Power Plant 
  - Wind Turbine Farm
  - Grandma's Solar Panel Farm

- (?) Store - gangs could control and open a shop (vending machines from Rust style) in order to sell items to other gangs while they are not on

  ​

------



### Monetization

#### Merchandise ideas 

- Custom weapon in the game (Weapon with admin-chosen bonuses, donator-chosen name)
- Bj really wants Patreon
- Custom particle effects (toggle-able)
- Vanity (Firework explosion on join, lightning strike on leave)
- Particle trails (toggle-able)
- Custom pets? https://www.spigotmc.org/resources/epicpets-v1-8-1-12-great-for-eula-pet-plugin.40697/ https://github.com/FeepsDev/EpicPets/wiki/Your-own-textures
- Chat titles
- Rename guild
- Nicknames (fuck)
- Guild decorations
- Arrow trails https://github.com/SkyLordJay/ArrowParticles/blob/master/src/me/skylordjay_/arrowtrails/Trail.java
- Balloons? 
- Death animations
- Taunts https://www.youtube.com/watch?v=j-BlVzi2XWo
- Lootboxes
  - contain pets, trails, effects, titles
- Team banner 
- Team totem

#### Gateway options

Custom web portal + Paypal/Stripe (i want to use stripe)

https://buycraft.net/ - used to use this for old servers but you have to pay for decent features



------



### Inspirations

Thank you to the following for assistance in the brainstorming process:

- Arma 3: Antistasi Altis

- EVE Online

- Grand Theft Auto: Online

- Minecraft Prison Servers

- Clash of Clans

- ROBLOX War Clans

- Rust

  ​

#### ROBLOX War Clan Unique Features

- High amount of user content creation and freedom

- Hugely open ended where the game is essentially built by the players

  - Creation of weapons, uniforms, bases, basically any asset used
  - Also the player made factions
  - Freedom is restricted only by the players' imagination and what other players deem fair and unfair

- Basically giving the players an engine and a website with the ability to upload stuff n the group system and blam they take it and run

- However where the game fails, is its' lack of definition in rules bc letting players define rules ruins game

  ​

#### Colin and I's Theoretical Ideas based off of War Clans

- Streamline content creation, provide players with more guidance
- Provide specific sections like "uniform creator" and "base editor" and the u click a button that says apply n then blam any time ur in game with ur group that stuff shows up on ur like side of the map or however it is we go about this
- Level editor will not provide the ability to make like invisible teleports or anything like ROBLOX did
- Provide more specialized, controlled, sandboxed tools, provide a set of rules or bounds, and go
- Rules and bounds can be carried out in the form of game modes (no debate about who won a TDM/KOTH)
- No voided games because of admin abuse (because there's no need for player admins)

Extended:

- Two sides of the map, your "prefab" side connects to their "prefab" side
- Another base idea
  - Sorta like territorial conquest of planetside
  - U design a base at each node
  - Randomize terrain they have to work with
  - They design structure inside of it
  - Game could stitch maps together
  - You capture a node, design base, if you lose node enemy base returns, if you regain node your base comes back.
  - Nodes could have different types of resources or things you would want to capture (strategic points)
    - Resources, factories, refineries,
    - Power plants (Wind turbine vs Nuclear Power Plant)
    - Iron-works vs hole in the ground
  - Base structures take resources to construct
  - Lots of opportunities to design neat structures ^
  - People can earn personal funds vs clan funds
  - Player can contribute personal funds to clan or save for themselves
  - Player requires funds to start their own clan, get their own starting node
- Would you be in a level editor or place things in world as player
  - Could be open ended, could depend on group settings, "level of structure", etc
  - Depends on whether we do node system or something else
- Warframe clan dojo
  - Specify what rank can plan rooms
  - Specify what rank can contribute (farmed) resources to further construction and progression of planned rooms
- How would battle server work if you have a bunch of adjacent nodes fighting in one battle?
  - Originally thinking we don't have one continuous map like PlanetSide
  - Sectors with small amount of nodes?



------



#### Meet the Team

- **Stellaric** (*ckhks, ckhawks, ToasterHQM, MeltedExistence*) - Project Lead - used to run crappy prison servers; has big ideas but never finishes them. has a lot of names
- **WillOCN** (*directive*) - Technical Lead - likes computer science
- **DiscoCrows** - Architecture Lead - likes blocks I think
- **TheProjectLemon** (*Greenaric*) - Game Design Lead - likes indie games a lot 
- **PurePoet** - Art Lead - can make things PRETTY
- **DeadN0tSleeping** - Developer - took BC Calculus his senior year
- **Guitaric** - Developer - likes guitars? I think?
- **Lordofpoop** (*gonzalez1939, sierek_1337*) - Builder, Game Designer, Developer - can never keep his account from getting stolen
- **EpicArcher518** - Developer - administrates a crappy network



------



#### Resources

Your IP: 107.214.84.120

Dynmap - http://107.214.84.120:8123/

screen - http://wiki.networksecuritytoolkit.org/index.php/HowTo_Share_A_Terminal_Session_Using_Screen

ProtocolLib - https://www.spigotmc.org/resources/protocollib.1997/

FastAsyncWorldEdit (+voxelsniper) - https://github.com/boy0001/FastAsyncWorldedit/wiki/WorldEdit-and-FAWE-patterns

MySQL Integration - https://www.spigotmc.org/wiki/mysql-database-integration-with-your-plugin/

Configuration File - https://www.spigotmc.org/wiki/creating-a-config-file/

Docker utilization - https://www.spigotmc.org/threads/deploying-minecraft-servers-using-docker-and-git.106001/

HTTP Sample - https://github.com/NiklasEi/LMGTFY/blob/master/src/main/java/me/nikl/lmgtfy/LmgtfyCommand.java

Colored Wool - https://www.spigotmc.org/threads/how-to-get-different-colored-wool.72248/

Model Creator - https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-tools/2146545-opls-model-creator-free-3d-model-editor

Another Modeler - https://grmodeler.github.io/modeler.html

Another Modeler - https://mrcrayfish.com/tools?id=mc

Modelers - https://minecraft.gamepedia.com/Programs_and_editors/3D_modeling

Dynmap Integration Sample - https://github.com/webbukkit/Dynmap-PreciousStones/blob/master/src/main/java/org/dynmap/preciousstones/DynmapPreciousStonesPlugin.java

Dynmap Marker Docs - https://github.com/webbukkit/dynmap/wiki/Using-markers

WorldPainter Trees/Rocks - https://www.planetminecraft.com/project/conquered_-tree-and-rocks-bo2schematics-for-terraformers/

Guild Sample 1 - https://github.com/darbyjack/Guilds-Plugin/blob/master/src/main/java/me/glaremasters/guilds/guild/Guild.java

Guild Sample 2 - https://github.com/darbyjack/Guilds-Plugin/blob/master/src/main/java/me/glaremasters/guilds/commands/CommandCreate.java

Guild Sample 3 - https://github.com/darbyjack/Guilds-Plugin/blob/master/src/main/java/me/glaremasters/guilds/guild/GuildHandler.java

Shipping Terms - http://onlinelibrary.wiley.com/doi/10.1002/9781119199762.app4/pdf

Player Heads for building and menus - https://freshcoal.com/

Progress bar calculation for capturing points https://www.spigotmc.org/threads/progressbar-calculation.189511/

Spigot DiscordBot API - https://www.spigotmc.org/resources/discord-bot-api-jda.49783/

Player nameplate https://github.com/sgtcaze/NametagEdit https://www.youtube.com/watch?v=5bAhhY8FyaE

Bungee Plugins - https://www.spigotmc.org/resources/bungeetablistplus.313/

https://www.spigotmc.org/resources/bungeeservermanager.7388/

set view-distance to 7 before production https://www.spigotmc.org/wiki/spigot-configuration/

AdvancedBan https://www.spigotmc.org/resources/advancedban.8695/



------



### Old, Scrapped Ideas

Trying to brainstorm names

- Corporation, Gang, Guild, Clan
- Corporation Conquest (corpque.st)
- Turf wars
- Gang wars - too overused



- revamped prison server
  - after u get free, u can purchase world instances?
  - buy blocks for expontential prices (almost like a creative world)
- some sort of battle royale
  - web front end for stats
- some sort of discord integration (or other integration)
  - lobby system such as RL 6 mans
- competitive minecraft 6man team queuing
- MMR (elo)/matchmaking system ?
- try to stay away from mods
- builds
  - AJ, Rhett, Disco?
  - Eli, Ian, QBMC
- Discord musicbot - Minecraft Block audio visualizer
- Prison - large scale map, subway system for fast travel, must pay to use
  - Large scale map could result in turf-war type of persistent mode, large scale capture the monument. 
    - Zones could require upkeep in resources
      - resources could be delivered to chest of some sort, more resources = more defenses added (players do not manually build them, automatic additions)
      - Decay over time with lack of upkeep
    - Separate team tiers, you only battle other teams with similar ELO or team size
- Prison could have cell blocks differentiated by biome, genre/era (medieval, futuristic, downtown), or just by general aesthetic feel
  - WorldPainter'd map
- Discord alerts for things happening in-game
  - Can be personalized as well
    - "Your teams' based is being ambushed! Hurry and defend it!"