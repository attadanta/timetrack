# Timetrack

A utility for exporting [hamster](https://projecthamster.wordpress.com/)'s database to Excel.

Hamster is the time tracker for gnome, that's what its tagline says. This utility's
purpose is to collect the activities of the current day and write a summary in an
excel sheet.

Timetrack collects the following fields on each activity:

 * name,
 * category,
 * description,
 * start and end time;

Activities with equal `name`, `category` and `description` will be summarized in a single
activity and their durations summed up when exporting.

Timetrack allows to define a schema (formatted as a `.properties` file) to use when writing
into the sheet.

Further documentation still pending.

## Dependencies

 * Apache POI
 * sqlite-jdbc

## Roadmap

See [TODO](TODO.md).

## License

Copyright (c) 2014 Kiril Tonev. See [LICENSE.txt](LICENSE.txt) for further details.