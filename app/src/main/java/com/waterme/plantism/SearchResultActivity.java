package com.waterme.plantism;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.waterme.plantism.data.PlantContract;
import com.waterme.plantism.data.PlantDbHelper;
import com.waterme.plantism.model.PlantIntro;
import com.waterme.plantism.utils.DatabaseUtils;


// needs a dbhelper to search
public class SearchResultActivity extends BaseActivity implements
        SearchView.OnQueryTextListener{

    private static final String TAG = "Search Activity";
    private static final int ID_INTRO_LOADER = 23;

    PlantDbHelper dbHelper;
    private SearchAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private int mPosition = RecyclerView.NO_POSITION;

    void initialize_database(PlantDbHelper dbHelper){
        //Todo update the database according to the googledoc https://docs.google.com/spreadsheets/d/11kN9Iur8yV2485e3hzg0VzqGO_HUI9m1yf4mOg8JcoE/edit?usp=sharing
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        DatabaseUtils utils = new DatabaseUtils();
        PlantIntro intro1 = new PlantIntro(
                "Donkey Burros Tails",
                "Succulents",
                "Burro’s tail is a heat and drought tolerant plant well suited for warm to temperate regions. The thick stems appear woven or plaited with leaves. The succulent is green to gray green or even blue green and may have a slight chalky look. ",
                "Sedum morganianum, or Burro’s tail, is a succulent perennial plant native to Mexico. ",
                "prefers bright, indirect sunlight. It will burn in strong, hot sun.\n" +
                        "\n" +
                        "watering once a month should be plenty, as the leaves hold quite a bit of moisture\n" +
                        "\n" +
                        "this plant should ideally be a lovely blue-green.\n",
                "donkey",
                100,
                100

        );
        PlantIntro intro2 = new PlantIntro(
                "Afra",
                "Succulents",
                "It is a soft-wooded, semi-evergreen upright shrub or small tree, usually 8–15 ft tall. afra has smaller and rounder pads and more compact growth It is much hardier, faster growing, more loosely branched, and has more limber tapering branches than Crassula once established.",
                "Portulacaria afra (known as elephant bush, dwarf jade plant, porkbush and spekboom in Afrikaans) is a small-leaved succulent plant found in South Africa.",
                "do not water too often, as Afra grows from desert. * put the plant into Sunshine.*" +
                        "prefer warm place rather than cold place",
                "afra",
                100,
                100

        );

        PlantIntro intro3 = new PlantIntro(
                "Desert Rose",
                "Succulents",
                "Adeniums are arid land plants native to sub-Saharan Africa and although there are several species, Adenium obesum is the one that's frequently grown as an ornamental. The common name is Desert Rose and when plants are in bloom, they create a spectacular floral display. ",
                "Adeniums are well-loved for their gorgeous flowers and their bulbous, caudiciform trunks. They are highly sought after plants and can remain a manageable size for years making them valuable container plants. ",
                "native to regions with poor, gritty soil and hot, sunny climates. *Desert rose succulents like bright light, so a southern window exposure provides enough sun for the plants to flourish and bloom. *Soil is very important and should have a mixture of cactus soil with gritty sand or lava rocks for good drainage.",
                "desertrose",
                100,
                100

        );

        PlantIntro intro4 = new PlantIntro(
                "Lety's Sevederia",
                "Succulents",
                "Sedeveria ‘Letizia’ is a beautiful small succulent plant, branching at the base with a cluster of stems that grow up to 8 inches tall, with up to 2.5 inches wide rosettes, bearing many tightly arranged up to 1 inch long, green, deltoid shaped leaves with fine hairs along the margins.  ",
                "Tree is tall ",
                "They will thrive in conditions that many other plants thrive in. *They are ideal for that part of your yard that gets too much sun or too little water to grow anything else. ",
                "lety",
                100,
                100

        );

        PlantIntro intro5 = new PlantIntro(
                "Banksianae",
                "rose",
                "It is a scrambling shrubby vine growing vigorously to 20 ft tall. Unlike most roses, it is practically thornless, though it may bear some prickles up to 5 mm long, particularly on stout, strong shoots. It is amongst the earliest flowering of all roses, usually appearing during May in the northern hemisphere, though cold weather can delay flowering.  ",
                "Lady banksiae rose, a climbing plant commonly referred to as \"Lady Banks’ Rose,\" are characterized by their limited amount of thorns and their small, pale yellow or off-white flowers and groups of multi-petal blooms. ",
                "Water the \"Lady Banksia\" every seven to 10 days after planting it. *After vigorous growth begins, decrease the frequency of watering, allowing enough time for the soil to dry before watering again. *Remove all dead branches and stems from the \"Lady Banksia\" with pruning shears while wearing gardening gloves.",
                "banksianae",
                100,
                100

        );
        PlantIntro intro6 = new PlantIntro(
                "Caninae",
                "rose",
                "It is a deciduous shrub normally ranging in height from 3.3–16.4 ft, though sometimes it can scramble higher into the crowns of taller trees. Its stems are covered with small, sharp, hooked prickles, which aid it in climbing. The leaves are pinnate, with 5–7 leaflets. The flowers are usually pale pink, but can vary between a deep pink and white. ",
                "Rosa canina, commonly known as the dog rose, is a variable climbing, wild rose species native to Europe, northwest Africa, and western Asia. ",
                "Grow dog rose plants in well-drained potting mix, or soil mixed with equal parts sand or compost. *Roses prefer a soil pH of 5.5 to 6.5. Water dog rose plants deeply about twice a week.* Fertilize dog rose plants with 1/2 cup of 10-10-10 granulated slow-release fertilizer once in March. ",
                "caninae",
                100,
                100

        );
        PlantIntro intro7 = new PlantIntro(
                "Carolina",
                "rose",
                "Carolina Rose is a member of the Rosaceae  family. The rose family includes well-known species as diverse as garden roses, strawberries, apples, peaches, and blackberries. Flowers have a base petal number of five, with many cultivated roses showing a hundred or more petals. ",
                "Rosa carolina, commonly known as the Carolina rose, pasture rose, or low rose, is a shrub in the rose family native to eastern North America, where it can be found in nearly all US states and Canadian provinces east of the Great Plains. ",
                "Best grown in average, medium to wet, well-drained soil in full sun. Best flowering and disease resistance occur in full sun. *Water deeply and regularly (mornings are best). Avoid overhead watering. *Good air circulation promotes vigorous and healthy growth and helps control foliar diseases. ",
                "carolina",
                100,
                100

        );
        PlantIntro intro8 = new PlantIntro(
                "Chinensis",
                "rose",
                "Rosa chinensis, known commonly as the Chinese rose, is a member of the genus Rosa native to Southwest China in Guizhou, Hubei, and Sichuan Provinces. The species is extensively cultivated as an ornamental plant, originally in China, and numerous cultivars have been selected which are known as the China roses. ",
                "Tree is tall ",
                "Succeeds in most soils, preferring a circumneutral soil and a sunny position. Grows well in heavy clay soils. *Dislikes water-logged soils. Grows well with alliums, parsley, mignonette and lupins. *Garlic planted nearby can help protect the plant from disease and insect predation. ",
                "chinensis",
                100,
                100

        );

        /* Insert ContentValues into database and get first row ID back */
        long firstRowId1 = utils.insertOneRecord(database, intro1);
        long firstRowId2 = utils.insertOneRecord(database, intro2);
        long firstRowId3 = utils.insertOneRecord(database, intro3);
        long firstRowId4 = utils.insertOneRecord(database, intro4);
        long firstRowId5 = utils.insertOneRecord(database, intro5);
        long firstRowId6 = utils.insertOneRecord(database, intro6);
        long firstRowId8 = utils.insertOneRecord(database, intro8);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* Insert ContentValues into database and get first row ID back */
        super.onCreate(savedInstanceState);
        dbHelper = new PlantDbHelper(this);
        //TODO check if database already exists and initiallizes it
        this.deleteDatabase(PlantDbHelper.DATABASE_NAME);
        initialize_database(dbHelper);
        long id = 0;
        Log.d(TAG, "row id is " + id);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_search_result, contentFrameLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.ry_search_plant);
        mSearchView = (SearchView) findViewById(R.id.sv_plant);

        hidePlantIntro();
        Log.d(TAG, "query");
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + PlantContract.PlantEntry.TABLE_NAME, null);
        mAdapter = new SearchAdapter(this, cursor);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mSearchView.setOnQueryTextListener(this);

        /* get the search query from last search activity */
        Bundle bundle = getIntent().getExtras();
        String query = "";
        if (bundle != null) {
            query = bundle.getString("QUERY");
            /* set the search result and clear the focus */
            mSearchView.setQuery(query, true);
            mSearchView.clearFocus();
        }

        /* show the search results with query */
        showPlantIntro();
    }

    @Override
    public boolean onQueryTextChange(String query) {
        mAdapter.getFilter().filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mAdapter.getFilter().filter(query);
        return true;
    }

    private void showPlantIntro() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hidePlantIntro() {
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

}
