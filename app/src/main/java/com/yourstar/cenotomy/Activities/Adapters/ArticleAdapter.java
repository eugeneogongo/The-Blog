package com.yourstar.cenotomy.Activities.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.prof.rssparser.Article;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.yourstar.cenotomy.Activities.Startup;
import com.yourstar.cenotomy.Activities.ViewPost;
import com.youstar.bloggerssport.R;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by odera on 2/18/2018.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {



    public ArticleAdapter(ArrayList<Article> list, int template_post, Context applicationContext) {
        this.rowLayout = template_post;
        this.mContext = applicationContext;
        this.articles=list;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;

    }

    private List<Article> articles;
    private int rowLayout;
    private Context mContext;

    public ArticleAdapter( int rowLayout, Context context) {
        articles = new ArrayList<>();
        this.rowLayout = rowLayout;
        this.mContext = context;
    }


    @Override
    public long getItemId(int item) {
        // TODO Auto-generated method stub
        return item;
    }

    public void clearData() {
        if (articles != null)
            articles.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    private Palette createPaletteSync(Bitmap bitmap) {
        Palette p = Palette.from(bitmap).generate();
        return p;
    }


    private int getColor(Bitmap bitmap) {
        Palette p = createPaletteSync(bitmap);
        Palette.Swatch vibrantSwatch = p.getLightMutedSwatch();
        int backgroundColor = ContextCompat.getColor(mContext, R.color.colorAccent);
        // Check that the Vibrant swatch is available
        if (vibrantSwatch != null) {
            backgroundColor = vibrantSwatch.getRgb();
        }
        return backgroundColor;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final Article currentArticle = articles.get(position);

        Locale.setDefault(Locale.getDefault());
        Date date = currentArticle.getPubDate();
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf = new SimpleDateFormat("dd MMMM yyyy");
        final String pubDateString = sdf.format(date);

        viewHolder.title.setText(currentArticle.getTitle());

        //load the image. If the parser did not find an image in the article, it set a placeholder.
  if(currentArticle.getImage() == null){
      viewHolder.image.setVisibility(View.GONE);
      viewHolder.postdesc.setText(currentArticle.getDescription());
      BitmapDrawable drawable;

      drawable = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.placeholder);
      final Bitmap bitmap = drawable.getBitmap();
      viewHolder.card.setBackgroundColor(getColor(bitmap));

  }else{
      viewHolder.image.setVisibility(View.VISIBLE);
      Picasso.get()
             .load(currentArticle.getImage())
              .placeholder(R.drawable.placeholder)
              .fit()
              .centerCrop()
              .into(viewHolder.image, new Callback() {
                  @Override
                  public void onSuccess() {
                      BitmapDrawable drawable;

                      drawable = (BitmapDrawable) viewHolder.image.getDrawable();
                      final Bitmap bitmap = drawable.getBitmap();
                      int color = getColor(bitmap);
                      viewHolder.card.setBackgroundColor(color);

                      }

                  @Override
                  public void onError(Exception e) {
                      viewHolder.image.setVisibility(View.GONE);
                  }


              });

  }
        viewHolder.txtblogname.setText(StringUtils.capitalize(currentArticle.getAuthor()));

        viewHolder.category.setText("# " + currentArticle.getCategories().get(0));

        viewHolder.postdesc.setText(stripHtml(currentArticle.getDescription().substring(0,150)+"..."));
        viewHolder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(mContext, ViewPost.class);
           intent.putExtra("link",currentArticle.getLink());
            mContext.startActivity(intent);


        });

    }

    @Override
    public int getItemCount() {

        return articles == null ? 0 : articles.size();

    }

    public String stripHtml(String html) {
        Document doc = Jsoup.parse(html);

       return doc.text();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        ImageView image;
        TextView category;
        CardView card;
        TextView postdesc;
        TextView txtblogname,txtblog2;

        public ViewHolder(View itemView) {

            super(itemView);

            title = itemView.findViewById(R.id.postTitle);
            postdesc = itemView.findViewById(R.id.postdesc);
            image = itemView.findViewById(R.id.postCoverImage);
            category = itemView.findViewById(R.id.postCategory);
            card = itemView.findViewById(R.id.mylayout);
            txtblogname = itemView.findViewById(R.id.txtblogname);


        }
    }
}