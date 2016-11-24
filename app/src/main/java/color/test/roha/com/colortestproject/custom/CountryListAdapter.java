package color.test.roha.com.colortestproject.custom;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import color.test.roha.com.colortestproject.R;

import static android.R.attr.data;

class CountryListAdapter extends ArrayAdapter<CountryInfo> implements SectionIndexer {
    final private HashMap<String, Integer> alphaIndex = new LinkedHashMap<>();
    final private HashMap<String, Integer> countryPosition = new LinkedHashMap<>();
    private String[] sections;
    Context ctx;

    public CountryListAdapter(Context context) {
        super(context, R.layout.view_spinner_auth_phone_number, android.R.id.text1);
        this.ctx = context;
    }

    // The list of countries should be sorted using locale-sensitive string comparison
    public void setData(List<CountryInfo> countries) {
        // Create index and add entries to adapter
        int index = 0;
        for (CountryInfo countryInfo : countries) {
            final String key = countryInfo.locale.getDisplayCountry().substring(0, 1)
                    .toUpperCase(Locale.getDefault());

            if (!alphaIndex.containsKey(key)) {
                alphaIndex.put(key, index);
            }
            countryPosition.put(countryInfo.locale.getDisplayCountry(), index);

            index++;
            add(countryInfo);
        }

        sections = new String[alphaIndex.size()];
        alphaIndex.keySet().toArray(sections);

        notifyDataSetChanged();
    }

    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int index) {
        if (sections == null) {
            return 0;
        }

        // Check index bounds
        if (index <= 0) {
            return 0;
        }
        if (index >= sections.length) {
            index = sections.length - 1;
        }

        // Return the position
        return alphaIndex.get(sections[index]);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    public int getPositionForCountry(String country) {
        final Integer position = countryPosition.get(country);
        return position == null ? 0 : position;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent){
//
//        if(convertView==null){
//            //inflate the layout
//            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.view_spinner_auth_phone_number, parent, false);
//        }
//
//
//        CountryInfo info = getItem(position);
//        //movie item based on the position
//
//        //get the TextView and then set the text(movie name) and tag(show_Movie.i) values
//        TextView textViewItem = (TextView)convertView.findViewById(android.R.id.text1);
//        textViewItem.setText(info.countryCode);
//
//
//        return convertView;
//
//
//    }
}