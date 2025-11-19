package pt.ua.ihc1718.musicapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageSliderAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;

    private Integer [] images = {
            R.drawable.disturbed,
            R.drawable.aerosmith,
            R.drawable.nirvana
    };
    private String [] newsTitleArray = {
            "Disturbed lançam novo álbum",
            "Aerosmith em Portugal",
            "Portugueses adoram os Nirvana"
    };
    private String [] newsContentArray = {
            "A banda Norte-Americana espera ainda lançar o álbum este Verão.",
            "As lendas do Rock estarão de regresso ao nosso país!",
            "É oficial. Os Portugueses gostam da antiga banda de Grunge."
    };

    public ImageSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.images_slider_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.newsImage);
        TextView newsTitle = (TextView) view.findViewById(R.id.newsTitle);
        TextView newsContent = (TextView) view.findViewById(R.id.newsContent);

        imageView.setImageResource(images[position]);
        newsTitle.setText(newsTitleArray[position]);
        newsContent.setText(newsContentArray[position]);

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
