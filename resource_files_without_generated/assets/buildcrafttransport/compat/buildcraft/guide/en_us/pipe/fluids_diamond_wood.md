<lore>
Want to take a certain fluid out and transport it somewhere else? Just give it power and watch it flow.
</lore>
<no_lore>
A Wooden Diamond Fluid Pipe is used to extract selected fluids from a tank when given power.
</no_lore>

<recipes stack="buildcrafttransport:pipe_fluids_diamond_wood_colorless"/>

<chapter name="Pipe Mechanics"/>
When powered, It will extract the fluid selected by the filter into any other connected tanks/pipes.
The extraction of fluids will occur out of solid side of the pipe towards the clear facing sides.
If it has multiple clear facing sides, the the fluids will evenly split in each direction.

Some machines can output fluids directly into it without needing power (This will not respect the filter).
Using Pipe Plugs or painting pipes a different colour can stop pipes connecting.

<chapter name="Filtering"/>
Opening up the Wooden Diamond Fluid Pipe GUI will allow you to setup a filter.
Placing a bucket (or other fluid container) of the fluid you want in the slots will allow you to filter what it extracts.
You can use the Whitelist and Blacklist items to change what your filter does:
Whitelist: Only the fluids selected will be extracted.
Blacklist: Any fluid other than the fluid selected will be extracted.

<chapter name="Powering"/>
It can receive power from any MJ power source such as: an engine, a power adaptor, or a pipe pulsar.
<link to="buildcraftcore:block/engine_wood"/>
<link to="buildcrafttransport:item/plug_power_adaptor"/>
<link to="buildcraftsilicon:item/plug_pulsar"/> 
For each pulse of  power it receives, it will extract from the adjacent tank.
The more power a pulse has, the more fluids will be extracted per pulse.

<no_detail>
The Wooden Diamond Fluid pipe transports fluids to adjacent pipes and tanks at 1.6 buckets per second.
Fluid pipes have an internal fluid tank which can hold 1 bucket per side and 1 Bucket in the centre.
</no_detail>

<usages stack="buildcrafttransport:pipe_fluids_diamond_wood_colorless"/>