package com.tiviacz.pizzacraft.init;

//@ObjectHolder(PizzaCraft.MODID)
public class ModVillagers 
{
/*	public static VillagerProfession chef = new VillagerProfession(PizzaCraft.MODID + ":chef", PizzaCraft.MODID + ":textures/entities/chef.png", PizzaCraft.MODID + ":textures/entities/chef_zombie.png"); // = null;
//	public static VillagerCareer pizzaChef;
	
	public static void init()
	{
		if(ConfigHandler.enableChefVillager)
		{
			initCarrersAndTrades();
		}
	}
	
	public static void initCarrersAndTrades()
	{
	//	chef = new VillagerProfession(PizzaCraft.MODID + ":chef", PizzaCraft.MODID + ":textures/entities/chef.png", PizzaCraft.MODID + ":textures/entities/chef_zombie.png");
		
		VillagerCareer pizzaChef = new VillagerCareer(chef, "pizza_chef");
		
		pizzaChef.addTrade(1, new PizzaChefTradeList(16, 24, ModItems.FLOUR, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.FLOUR_CORN, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.CHEESE, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.ONION, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.PEPPER, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.OLIVE, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.BLACK_OLIVE, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.PINEAPPLE, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.TOMATO, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.CUCUMBER, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.CORN, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.BROCCOLI, 1, 1, Items.EMERALD));
		
		pizzaChef.addTrade(2, new PizzaChefTradeList(4, 6, Items.EMERALD, 1, 1, ModItems.SLICE_0),
				new PizzaChefTradeList(4, 6, Items.EMERALD, 1, 1, ModItems.SLICE_1),
				new PizzaChefTradeList(4, 6, Items.EMERALD, 1, 1, ModItems.SLICE_2),
				new PizzaChefTradeList(4, 6, Items.EMERALD, 1, 1, ModItems.SLICE_4),
				new PizzaChefTradeList(4, 6, Items.EMERALD, 1, 1, ModItems.SLICE_6));
		
		pizzaChef.addTrade(3, new PizzaChefTradeList(1, 1, Item.getItemFromBlock(ModBlocks.PIZZA_BOX_0), 1, 1, Items.EMERALD));
		
		pizzaChef = (new VillagerCareer(chef, "pizza_chef"))
				
				//lvl 1
				.addTrade(1, new PizzaChefTradeList(16, 24, ModItems.FLOUR, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.FLOUR_CORN, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.CHEESE, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.ONION, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.PEPPER, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.OLIVE, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.BLACK_OLIVE, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.PINEAPPLE, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.TOMATO, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.CUCUMBER, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.CORN, 1, 1, Items.EMERALD),
				new PizzaChefTradeList(16, 24, ModItems.BROCCOLI, 1, 1, Items.EMERALD))
				
				//lvl 2
				.addTrade(2, new PizzaChefTradeList(4, 6, Items.EMERALD, 1, 1, ModItems.SLICE_0),
				new PizzaChefTradeList(4, 6, Items.EMERALD, 1, 1, ModItems.SLICE_1),
				new PizzaChefTradeList(4, 6, Items.EMERALD, 1, 1, ModItems.SLICE_2),
				new PizzaChefTradeList(4, 6, Items.EMERALD, 1, 1, ModItems.SLICE_4),
				new PizzaChefTradeList(4, 6, Items.EMERALD, 1, 1, ModItems.SLICE_6)); 
	}
	
	public static class PizzaChefTradeList implements ITradeList
    {
        public Item itemToBuy;
        public Item itemToPay;
        public PriceInfo priceInfo;
        public PriceInfo countInfo;
        
        public PizzaChefTradeList(int minPrice, int maxPrice, Item itemToPay, int minCount, int maxCount, Item itemToBuy)
        {
            this.itemToBuy = itemToBuy;
            this.itemToPay = itemToPay;
            this.priceInfo = new PriceInfo(minPrice, maxPrice);
            this.countInfo = new PriceInfo(minCount, maxCount);
        }

        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
        {
            int actualPrice = 1;

            if(priceInfo != null)
            {
                actualPrice = priceInfo.getPrice(random);
            }
            
            int actualCount = 1;
            
            if(countInfo != null)
            {
            	actualCount = countInfo.getPrice(random);
            }

            ItemStack stackToPay = new ItemStack(this.itemToPay, actualPrice, 0);
            ItemStack stackToBuy = new ItemStack(this.itemToBuy, actualCount, 0);
            
            int i = recipeList.size();
            PriceInfo maxRecipeSize = new PriceInfo(2, 4);
            
            if(i >= maxRecipeSize.getPrice(random))
            {
            	return;
            }
            
            MerchantRecipe recipeToAdd = new MerchantRecipe(stackToPay, stackToBuy);
            
            if(random.nextBoolean())
            {
            	recipeList.add(recipeToAdd);
            }
        }
    }     */
}