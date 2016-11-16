# BookingBot
BookingBot is a bot that competes in the [Booking.com challenge](http://booking.riddles.io).
## Usage
BookingBot reads information that the game engine outputs to the console.

A debug mode is available in the [BotStarter](http://bitbucket.org/Silver292/bookingbot/src/1193872cfc0273b0d846f2f3d47a3a71947b6f15/BookingBot/src/uk/tlscott/BookingBot/BotStarter.java) class.
Setting this to true will allow the bot to read the debugData file.

A debugData file can be created from riddles.io game logs by copying the log into the debugData file and running the SanitizeTestData python script.

The package can be made ready for upload by running the ZipAndUpdatePackage python script.
This script turns off debugging on the output file and renames the packages.
The ready version is output to BookingBot.zip.