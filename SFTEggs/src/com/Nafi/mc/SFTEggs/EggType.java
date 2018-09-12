package com.Nafi.mc.SFTEggs;

public enum EggType {
	Pig((short)90),
	Cow((short)92),
	Zombie((short)54),
	Sheep((short)91),
	Elder_Guardian((short)4),
	Wither_Skeleton((short) 5),
	Stray((short) 6),
	Husk((short)23),
	Zombie_Villager((short) 27),
	Evoker((short) 34),
	Vex((short) 35),
	Vindicator((short)36),
	Creeper((short)50),
	Skeleton((short)51),
	Spider((short)52),
	Slime((short)55),
	Ghast((short) 56),
	Zombie_Pigman((short)57),
	Enderman((short)58),
	Cave_Spider((short)59),
	Silverfish((short)60),
	Blaze((short)61),
	Magma_Cube((short)62),
	Witch((short)66),
	Endermite((short)67),
	Guardian((short)68),
	Shulker((short)69),
	Skeleton_Horse((short)28),
	Zombie_Horse((short) 29),
	Donkey((short)31),
	Mule((short)32),
	Bat((short)65),
	Chicken((short)93),
	Squid((short)94),
	Wolf((short)95),
	Mooshroom((short)96),
	Ocelot((short)98),
	Horse((short) 100),
	Rabbit((short)101),
	Polar_Bear((short) 102),
	Llama((short)103),
	Villager((short)120);
	
	private short value;
	
	private EggType(short s)
	{
		this.value = s;
	}
	
	public short getValue()
	{
		return value;
		
	}
	
	public static class parse
	{
		public static short toEggValue(String s)
		{
			for(EggType e : EggType.values())
			{
				if(e.toString().equalsIgnoreCase(s))
				{
					return e.getValue();
				}
			}
			
			return -1;
		}
		
		public static EggType toEggType(String s)
		{
			for(EggType e : EggType.values())
			{
				if(e.name().equalsIgnoreCase(s))
				{
					return e;
				}
			}
			
			return null;
		}
		
	}
	
}
