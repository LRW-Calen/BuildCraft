<lore>
Imagine a pipe which can place blocks, break blocks and use blocks. This is that pipe your imagining.
</lore>
<no_lore>
A Striped Transport Pipe is used to break blocks, place blocks use items fed through it and transport them into a connecting pipe/inventory.
</no_lore>

<recipes stack="buildcrafttransport:pipe_items_stripes_colorless"/>

<chapter name="Pipe Operation"/>
When powered, It will break the block directly in front of it and pass it on to the adjacent pipe or inventory.
When a block is fed through it, It will place the block directly in front of it.
When an item is fed through it, It will attempt to 'use' it on the block/entity in front of it.

<chapter name="Pipe Direction"/>
An Striped Transport Pipe has to be 'pointing' in a direction to effect the block in front of it.
With multiple pipes/inventories connected to it, It will lose its ability affect from the world.

<chapter name="Pipe Mechanics"/>
If there is no where for the travelling items to go, they will be dropped out of the pipe onto the ground.
Using Pipe Plugs or painting pipes a different colour can stop pipes connecting.

<chapter name="Powering"/>
It can receive power from any MJ power source such as: an engine, a power adaptor, or a pipe pulsar.
<link to="buildcraftcore:block/engine_wood"/>
<link to="buildcrafttransport:item/plug_power_adaptor"/>
<link to="buildcraftsilicon:item/plug_pulsar"/> 
For each pulse of power it receives, it will use the power it received and attempt to break the block in front of it.
The more power a pulse has, the more power will be used to break the block.

<usages stack="buildcrafttransport:pipe_items_stripes_colorless"/>