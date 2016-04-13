package mobtime.henrique.com.br.unipet;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.beethovenheader)
                .addProfiles(
                        new ProfileDrawerItem().withName("Beethoven").withEmail("90 Kg | 100 cm | 8 anos").withIcon(R.drawable.beethoven)
                       // new ProfileDrawerItem().withName("Scooby").withEmail("60 Kg | 140 cm | 4 anos").withIcon(R.drawable.scooby)
                )
                .build();


        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("Minhas Vacinas").withIcon(R.drawable.ic_pets);

//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Meus Atendimentos").withIcon(R.drawable.ic_local_hospital),
                        item1,
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Tabela de Preços").withIcon(R.drawable.ic_monetization),
                        new SecondaryDrawerItem().withName("Quem Somos").withIcon(R.drawable.ic_people)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        switch (position){
                            case 4:
                                Intent it = new Intent(MainActivity.this, Precos.class);
                                startActivity(it);
                                break;
                        }
                        return true;
                    }
                })
                .build();


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
}
