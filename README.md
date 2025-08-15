# Doom Tracker

Track your cumulative floor and loot chances at the Doom of Mokhaiotl. 

Doom Tracker automatically records how many floors you’ve completed since your last drop for each item type - uniques, pet, cloth, eye, and treads - and calculates your cumulative drop chance based on the official rates for each floor.

Your progress is saved between sessions. You can toggle the plugin to show progress for each individual unique (turned off by default). 




Inspired by the [Pyramid Plunder Counter](https://runelite.net/plugin-hub/show/pyramid-plunder-counter).

## How is my drop chance calculated? 

The plugin shows your cumulative chance at receving at least one drop so far. Think of it as a running tally of your current "dryness." It first determines the probability of not receiving a drop for each specific floor you've completed. It then multiplies all of these individual "no-drop" probabilities together to get the total chance of not receiving a single item. This final number is then subtracted from 1 to give you the total cumulative probability of having received at least one drop. 

This number is different from the number of expected drops. To see your expected loot quantity, please use [this calculator](https://oldschool.runescape.wiki/w/Calculator:Doom_of_Mokhaiotl_Completions_Calculator) found on the wiki. 
