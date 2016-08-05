STT Collection Manager
Author: August Enzelberger
Project Homepage: https://github.com/augustenz/STT-Collection-Manager

STT Collection manager is a Java application, developed to help manage a Crew collection for Disruptor Beam's Star Trek Timelines game.

It consists of multiple tabs:

1) The Collection tab

This tab contains the Crew Collection table. Here all crew that is currently in the collection can be edited. The editable fields are all the skill fields, the fused stars field, the level field, the quantity field (in case of multiples) and a checkbox that removes crew from the collection.
Clicking on a column header will sort the table according to that column (this setting is saved).
Clicking on the Crew Name field, Race field or any of the Traits in the traits field will open the corresponding wiki page.

2) The Crew tab

This tab contains the Crew table. Here all crew in the game is listed, no fields are editable here except a checkbox that only allows adding crew to the collection.
Clicking on a column header will sort the table according to that column.
Clicking on the Crew Name field, Race field or any of the Traits in the traits field will open the corresponding wiki page.

3) The Log tab

This tab contains a text area where messages regarding the execution of commands can be seen.

4) The Charts tab

This tab contains four charts:

 - All Crew pie chart: A pie chart where the balance (quantity of crew/rarity) of the crew in the game can be seen. Each slice represents the number of crew in game of a certain rarity.
 
 - Crew Collection pie chart: A pie chart where the balance (quantity of crew/rarity) of the collection can be seen. Each slice represents the number of crew in the collection of a certain rarity.
 
 - Bar chart: A bar chart where on the left the bars represent the number of crew in the game of a certain rarity and on the right the number of crew in the collection of a certain rarity.
 
 - Stacked bar chart: Similar to the bar chart, only that the bars are stacked instead of being side-by-side.

Every chart will show the number that each graphic element represents as a mouse tooltip.
If a chart is clicked it is opened in a new window where it is enlarged and can be maximized.

5) The Stats tab

This tab has a text field where the number of crew slots can be entered. It has some information about the collection, such as the number of crew/crew slots, the number of crew in the collection per rarity/the number of crew in the game per rarity and the average crew level.

The Menu Bar

The menu bar consists of five menus: File, Search, Export, Skin, Font and Help.

1)File

 - Download Crew: Drops the crew table (the collection is kept intact) and refetches all the crew data from the wiki. This is usefull when new crew is added to the wiki.

 - Refresh Tables: Refreshes the data shown in the tables, affects only the view, not the data.

 - Exit: Saves various settings like the window size, window position etc, shutsdown the database and closes the application.

2) Search

Using the search function both tables can be filtered according to certain criteria. Typing in any text field will immediatly query the database and populate the tables accordingly.
Any info can be entered only partly and the matches (if any) will be shown.
Multiple traits can be entered (they can be seperated by a space or a comma) but there will be matches only if they are entered in the order that they appear in.
Single traits of course will be matched regardless of their position.

For the search window to open this menu item has to be pressed when either the Collection tab or the Crew tab are open. It will query the table that is open.

3) Export

 - Export All Crew: Exports all crew in the game to a HTML (crew in collection is shown in bold), text, or csv (good for Spreadsheets) file.

 - Export Crew Collection: Exports the crew collection to a HTML, text, or csv (good for Spreadsheets) file.

4) Skin

Lists skins available on the system and allows changing the current skin. This setting is saved.

5) Font

 - Increase Font Size: Increases the font size.
 - Decrease Font Size: Decreases the font size.
 - Reset Font Size: Resets the font size (12).
 - Chabge Font: Lists fonts found on the system and allows to change the font being used in the tables.

These settings ar saved.

6) Help

 - Help: Probably this readme file :)
 - Info: Info about the system.
 - About: The application credits.
