/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.prop;


import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

//public class UnlistedNonNullProperty<V> implements IUnlistedProperty<V>
public class UnlistedNonNullProperty extends Property<ComparableTilePropValue>
{
    public final String name;

    public UnlistedNonNullProperty(String name)
    {
        super(name, ComparableTilePropValue.class);
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }

//    @Override
//    public boolean isValid(V value)
//    {
//        return value != null;
//    }

    @SuppressWarnings("unchecked")
//    @Override
//    public Class getType()
//    {
//        return Object.class;
//    }

    @Override
    public Collection<ComparableTilePropValue> getPossibleValues()
    {
        return Collections.EMPTY_LIST;
    }


    @Override
//    public String valueToString(V value)
    public String getName(ComparableTilePropValue value)
    {
        return "";
    }

    @Override
    public Optional<ComparableTilePropValue> getValue(String name)
    {
        return Optional.empty();
    }
}
