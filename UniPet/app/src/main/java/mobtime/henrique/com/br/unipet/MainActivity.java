package mobtime.henrique.com.br.unipet;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import mobtime.henrique.com.br.unipet.adapters.CustomViewPager;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private CustomViewPager pagerAdapter;
    private ImageButton ibConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        //Blur Imagem Account Header
        Drawable acHeader = blurImage(R.drawable.beethoven);

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(acHeader)
                .addProfiles(
                        new ProfileDrawerItem().withName("Beethoven").withEmail("90 Kg | 100 cm | 8 anos").withIcon(R.drawable.beethoven)
                        // new ProfileDrawerItem().withName("Scooby").withEmail("60 Kg | 140 cm | 4 anos").withIcon(R.drawable.scooby)
                )
                .build();


        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIcon(R.drawable.ic_home_black_24dp),
                        new PrimaryDrawerItem().withName("Meus Exames").withIcon(R.drawable.ic_local_hospital),
                        new PrimaryDrawerItem().withName("Calendário de Vacinação").withIcon(R.drawable.ic_date),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Tabela de Preços").withIcon(R.drawable.ic_monetization),
                        new SecondaryDrawerItem().withName("Quem Somos").withIcon(R.drawable.ic_people)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        switch (position) {
                            case 1:
                                Intent it1 = new Intent(MainActivity.this, Launcher.class);
                                startActivity(it1);
                                break;
                            case 2:
                                Intent it2 = new Intent(MainActivity.this, MeusExames.class);
                                startActivity(it2);
                                break;
                            case 3:
                                Intent it3 = new Intent(MainActivity.this, CalendarioVacinacao.class);
                                startActivity(it3);
                                break;
                            case 5:
                                Intent it5 = new Intent(MainActivity.this, Precos.class);
                                startActivity(it5);
                                break;
                        }
                        return true;
                    }
                })
                .build();

        //SETA VIEWPAGER
        vp = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new CustomViewPager(this);
        vp.setAdapter(pagerAdapter);

        ibConsulta = (ImageButton) findViewById(R.id.ibConsulta);
        ibConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, Agendamento.class);
                startActivity(it);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Drawable blurImage(int res){
        Bitmap icon = BitmapFactory.decodeResource(getResources(), res);
        final RenderScript rs = RenderScript.create(this);
        final Allocation input = Allocation.createFromBitmap( rs, icon, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT );
        final Allocation output = Allocation.createTyped( rs, input.getType() );
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create( rs, Element.U8_4(rs) );
        script.setRadius(20);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(icon);
        return new BitmapDrawable(getResources(),icon);
    }
}
