# NBP-API
This app is a solution to recrutation task at Currenda (currenda.pl), which was to use an API at http://api.nbp.pl/ to extract exchange rates from given period for given currency and print its avarage buying rate, as well as standard deviation of its selling rate.

# Input explaination
currency code - in 3 letter code e.g. EUR,USD,CHF,GBP starting date - in format YYYY-MM-DD ending date - in same format

all those parameters in one line seperated with whitespaces

# Input example
EUR 2017-11-20 2017-11-24

should give

4.1815 4.0101
