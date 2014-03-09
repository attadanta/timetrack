# Timetrack

A utility for exporting [hamster](https://projecthamster.wordpress.com/)'s database to Excel.

Hamster is the time tracker for gnome, that's what its tagline says. This utility's
purpose is to collect the activities of the current day and write a summary in an
excel sheet.

Timetrack collects the following fields on each activity:

 * `name`,
 * `category`,
 * `description`,
 * start and end time

Activities with equal `name`, `category` and `description` will be summarized in a single
activity and their durations summed up when exporting.

Timetrack allows to define a schema (formatted as a `.properties` file) to use when writing
into the sheet.

Further documentation still pending.

## Dependencies

 * Apache POI
 * sqlite-jdbc

## Roadmap

See [TODO](TODO.md)

## License

(The MIT License)

Copyright © 2014 Kiril Tonev

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the 'Software'), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.