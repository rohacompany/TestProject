package color.test.roha.com.colortestproject.custom;

import java.text.Collator;
import java.util.Locale;

class CountryInfo implements Comparable<CountryInfo> {
    private final Collator collator;
    public final Locale locale;
    public final int countryCode;

    public CountryInfo(Locale locale, int countryCode) {
        collator = Collator.getInstance(Locale.getDefault());
        collator.setStrength(Collator.PRIMARY);

        this.locale = locale;
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CountryInfo that = (CountryInfo) o;

        if (countryCode != that.countryCode) return false;
        return !(locale != null ? !locale.equals(that.locale) : that.locale != null);

    }

    @Override
    public int hashCode() {
        int result = locale != null ? locale.hashCode() : 0;
        result = 31 * result + countryCode;
        return result;
    }

    @Override
    public String toString() {
        return this.locale.getDisplayCountry() + "(+" + countryCode + ")";
    }

    @Override
    public int compareTo(CountryInfo info) {
        return collator.compare(this.locale.getDisplayCountry(), info.locale.getDisplayCountry());
    }
}