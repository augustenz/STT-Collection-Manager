<b>STT Collection Manager</b> ![Logo](https://github.com/augustenz/STT-Collection-Manager/blob/master/src/sttcollectionmanager/resources/logo48x48.png)

STT Collection Manager is a desktop Java application, developed to help manage a crew collection for Disruptor Beam's Star Trek Timelines game.

<img src="https://github.com/augustenz/STT-Collection-Manager/blob/master/AllCrew.png" width="15%" height="15%" /><img src="https://github.com/augustenz/STT-Collection-Manager/blob/master/Collection.png" width="15%" height="15%" /><img src="https://github.com/augustenz/STT-Collection-Manager/blob/master/SearchCollection.png" width="15%" height="15%" /><img src="https://github.com/augustenz/STT-Collection-Manager/blob/master/Charts.png" width="15%" height="15%" /><img src="https://github.com/augustenz/STT-Collection-Manager/blob/master/ChartsWindow.png" width="15%" height="15%" />

Download the latest version: <a href="https://github.com/augustenz/STT-Collection-Manager/raw/master/STTCollectionManager_v1.2.zip">STTCollectionManager_v1.2.zip</a>

[![Donate](https://github.com/augustenz/STT-Collection-Manager/blob/master/src/sttcollectionmanager/resources/beer32x32.png)](https://www.paypal.me/augustenz) [![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.me/augustenz) If you like this Software feel free to buy me a beer! :)

16/10/2017
Version 1.2

New Features:
  - Added Settings tab (change every URL in the app)
  - Added Import function (import crew data from .xls files for both Collection and Crew Tables)
  - Added faster way to add copies to collection (press an already checked checkbox in the Crew table)
  - Clicking or tabbing table cells auto selects-all cell content for faster editing
  - Collection table can now be exported to HTML too
  - All table rows in exported HTML files are now colored by Crew rarity
  - HTML tables are now sortable by any column (clicking on table headers toggles Asc/Desc sort order)
  - Progress window with a text area and a progress bar for import tasks

Bugs/Performance
  - Fixed bug where the splash screen wouldn't show
  - Fixed bug where tabbing through the Collection cells would delete an entry
  - Workaround for a bug which would add the first member in the Collection twice (first run only)
  - Various other performance optimizations and bug fixes

11/8/2016
Version 1.1

- Added: Highlight row under cursor
- Stacked Bar colors changed
- 1-click cell edit
- Tab to next editable cell
- Crew in collection update fixes - improved performance

5/8/2016
Version 1.0 (Initial release)

- Removed table tooltips
- Fixed crew in collection SQL update (single quote escape)

=============
  Help File
=============

STT Collection Manager v1.2
Author: August Enzelberger
Project Homepage: https://github.com/augustenz/STT-Collection-Manager
Date: 16/10/2017

STT Collection Manager is a Java application, developed to help manage a Crew collection for Disruptor Beam's Star Trek Timelines game.

=====================================================================
Important Info

This application needs the latest JRE (Java Runtime Environment) to run, it is available for various systems and can be obtained
from www.oracle.com (or https://java.com/en/download/).

JavaFX is used by this application to display charts, it is included in Oracle's JRE so nothing else is needed if that JRE is installed.
Other JREs (like OpenJDK in Linux) need extra packages (like OpenJFX in Linux) to run Java applications that contain JavaFX elements.
In that case either the extra packages can be installed, or Oracle's JRE.
JavaFX is required to run this application.

The database is saved in a folder with the name "STTCollectionManager" in the user's home directory, or in the directory containing the application.

To run the application, double-click on the STTCollectionManager.jar file, or right click and choose 
open with and your JRE (java.exe on Windows, OpenJDK or Oracle Java on Linux).

To run the project from the command line, go to the application folder and 
type the following:


java -jar "STTCollectionManager.jar"

The Shortcut icons folder contains icon images of various sizes if you wish to make a shortcut
for the application.

=====================================================================
STT Collection Manager consists of six tabs:

1) The Collection tab

This tab contains the Crew Collection table. Here all crew that is currently in the collection can be edited. The editable fields are all the skill fields,
the fused stars field, the level field, the quantity field (in case of multiples) and a checkbox that removes crew from the collection.
Clicking on an editable cell opens it for editing and auto selects-all content for faster editing.
While editing a cell, pressing Tab on the keyboard will move to the next cell.
Tabbing will cycle through all editable columns (except Quantity) and all rows.
Clicking on a column header will sort the table according to that column.
Clicking on the Crew Name field, Race field or any of the Traits in the traits field will open the corresponding wiki page.
Right-clicking on crew members brings up a menu that allows to make a copy of the selected crew member (multiples but with different stats).

2) The Crew tab

This tab contains the Crew table. Here all crew in the game is listed, no fields are editable here except a checkbox that allows adding crew to the
collection.
If this checkbox is already checked, additional clicks on the checkbox will create copies in the Collection (faster way for adding duplicates).
Clicking on a column header will sort the table according to that column.
Clicking on the Crew Name field, Race field or any of the Traits in the traits field will open the corresponding wiki page.

3) The Log tab

This tab contains a textarea where messages regarding the execution of commands can be seen.

4) The Charts tab

This tab contains four charts:

 - All Crew pie chart: A pie chart where the balance (quantity of crew/rarity) of the crew in the game can be seen. Each slice represents the number
of crew in game of a certain rarity.
 
 - Crew Collection pie chart: A pie chart where the balance (quantity of crew/rarity) of the collection can be seen. Each slice represents the number
of crew in the collection of a certain rarity.
 
 - Bar chart: A bar chart where on the left the bars represent the number of crew in the game of a certain rarity and on the right the number of crew
in the collection of a certain rarity.
 
 - Stacked bar chart: Similar to the bar chart, only that the bars are stacked instead of being side-by-side.

Every chart will show the number that each graphic element represents as a mouse tooltip.
If a chart is clicked it is opened in a new window where it is enlarged and can be maximized.

5) The Stats tab

This tab has a text field where the number of crew slots can be entered. It has some information about the collection, such as the number of crew/crew
slots, the number of crew in the collection per rarity/the number of crew in the game per rarity and the average crew level.

6) The Settings tab

This tab has three text fields where the URLs used in the app can be changed.

=====================================================================
The Menu Bar

The menu bar consists of seven menus: File, Search, Import, Export, Skin, Font and Help.

1) File

 - Download Crew: Drops the crew table (the collection is kept intact) and re fetches all the crew data from the wiki. This is useful when new crew is
added to the wiki.

 - Refresh Tables: Refreshes the data shown in the tables, affects only the view, not the data.

 - Exit: Saves various settings like the window size, window position etc, shuts down the database and closes the application.

2) Search

Using the search function, both tables can be filtered according to certain criteria. Typing in any text field will immediately query the database and
populate the tables accordingly.
Any info can be entered only partly and the matches (if any) will be shown.
Multiple traits can be entered (they can be separated by a space or a comma) but there will be matches only if they are entered in the same order that they
appear in.
Single traits of course will be matched regardless of their position in the field.

For the search window to open, this menu item has to be pressed when either the Collection tab or the Crew tab are open. It will filter the table that is
open.

3) Import

 - Import All Crew: Imports crew data from an .xls file (tab seperated text file) to the "All Crew" table.

 - Import Crew Collection: Imports crew data from an .xls file (tab seperated text file) to the "Crew Collection" table.

4) Export

 - Export All Crew: Exports all crew in the game to a HTML (crew in collection is shown in bold), text, or xls (tab seperated text file - good for Spreadsheets) file.

 - Export Crew Collection: Exports the crew collection to a HTML, text, or xls (tab seperated text file - good for Spreadsheets) file.

5) Skin

Lists skins available on the system and allows changing the current skin. This setting is saved.

6) Font

 - Increase Font Size: Increases the font size.
 - Decrease Font Size: Decreases the font size.
 - Reset Font Size: Resets the font size (12).
 - Change Font: Lists fonts found on the system and allows to change the font being used in the tables.

These settings are saved.

7) Help

 - Help: Probably this readme file :)
 - Info: Info about the system.
 - About: The application credits.

============
  Credits
============

Author: August Enzelberger
Project Webpage: https://github.com/augustenz/STT-Collection-Manager

STT Collection Manager was created with Netbeans IDE 8.1.

Crew data comes from the very awesome Star Trek Timelines Wiki (http://startrektimelineswiki.com) and its awesome users.

App icon by Memory Alpha (http://memory-alpha.wikia.com).

Roddenberry font by Jaynz (http://www.fontspace.com/pixel-sagas/roddenberry).

Icons from the Noun Project (https://thenounproject.com): Info by Karthick Nagarajan, Export by Mourad Mokrane, Refresh by Alex Auda Samora, Download by Guillaume Bahri, Zoom out by useiconic.com, Zoom in by useiconic.com, Reset zoom by Leonardo Schneider, Font by iconsmind.com, Help by Dima Lagunov, Log by Arthur Shlain, Stats by Calvin Goodman, Charts by Prerak Patel, About by Gregor Črešnar, Skin by Rflor, Exit by Andres Gleixner, Tables by Viktor Vorobyev, Search by Iconfactory Team, Settings by Michal Beno.

JFontChooser by Masahiko Sawai (http://jfontchooser.osdn.jp/site/jfontchooser).

Nimbus checkbox alternate row background color bug fix by Harald Barsnes (https://github.com/compomics).

Many thanks to all the awesome people at Stack Overflow (http://stackoverflow.com) and Coderunch (https://coderanch.com), without all of the excellent information there this project would have been much harder to develop.

This software is released under the GNU General Public License, version 3 (https://www.gnu.org/licenses/gpl-3.0.html).

Game content and materials are trademarks and copyrights of their respective publisher and its licensors.
