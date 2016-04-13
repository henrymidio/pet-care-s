package mobtime.henrique.com.br.unipet.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import mobtime.henrique.com.br.unipet.R;

/**
 * Created by Henrique on 11/04/2016.
 */
public class CustomViewPager extends PagerAdapter {

    private Context ctx;
    private LayoutInflater li;
    private int[] images = {R.drawable.dog};

    public CustomViewPager(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = li.inflate(R.layout.swipe_pager_adapter, container, false);
        ImageView iv = (ImageView) item_view.findViewById(R.id.ivPager);
        iv.setBackgroundResource(images[position]);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
