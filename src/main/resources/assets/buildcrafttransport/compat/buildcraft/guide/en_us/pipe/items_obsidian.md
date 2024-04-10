<lore>
Picking up items from the world and moving them around is the future!
</lore>
<no_lore>
A Obsidian Transport Pipe is used to pick up in world items and transport them into a connecting pipe/inventory.
</no_lore>

<recipes stack="buildcrafttransport:pipe_items_obsidian_colorless"/>

<chapter name="Pipe Operation"/>
When powered, It can pick up items in a 3x4x3 area above it.
Without power it will still pick up any items which come into contact with the pipe.

<chapter name="Pipe Direction"/>
An Obsidian Transport Pipe has to be 'pointing' in a direction to have an area of effect.
If the pipe has multiple connections, It will lose its ability to pick up items from the world.

<chapter name="Pipe Mechanics"/>
If there is no where for the received items to go, they will be dropped out of the pipe onto the ground.
Using Pipe Plugs or painting pipes a different colour can stop pipes connecting.

<chapter name="Powering"/>
It can receive power from any MJ power source such as: an engine, a power adaptor, or a pipe pulsar.
<link to="buildcraftcore:block/engine_wood"/>
<link to="buildcrafttransport:item/plug_power_adaptor"/>
<link to="buildcraftsilicon:item/plug_pulsar"/> 
For each pulse of power it receives, it will pick up items from the ground.
The more power a pulse has, the more items will pick up per pulse.

<usages stack="buildcrafttransport:pipe_items_obsidian_colorless"/>