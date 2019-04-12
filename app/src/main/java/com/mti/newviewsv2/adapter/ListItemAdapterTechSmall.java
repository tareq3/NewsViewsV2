/*
 * Created by Tareq Islam on 2/19/19 9:06 PM
 *
 *  Last modified 2/19/19 9:05 PM
 */

/*
 * Created by Tareq Islam on 2/15/19 4:01 PM
 *
 *  Last modified 2/14/19 9:58 PM
 */

package com.mti.newviewsv2.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mti.newviewsv2.R;
import com.mti.newviewsv2.model.Article;

import java.util.ArrayList;
import java.util.List;

public class ListItemAdapterTechSmall extends RecyclerView.Adapter<ListItemAdapterTechSmall.ListItemViewHolder>{

    private ItemClickListener mItemClickListener; /*Interface for item click callback*/

    /*View Holder Class*/
    class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener{

        ImageView cardImage;
    TextView cardTitle,cardContent,cardAuthor,cardDate,cardSource; /*Views for cards*/

    /* Constructor*/
    ListItemViewHolder(View itemView) {
        super(itemView);
        /*Add the view for cards*/
        cardImage=itemView.findViewById(R.id.cardImg);
        cardTitle=itemView.findViewById(R.id.card_title);
        cardContent=itemView.findViewById(R.id.card_content);
        cardAuthor=itemView.findViewById(R.id.card_author);
        cardDate=itemView.findViewById(R.id.card_date);
        cardSource=itemView.findViewById(R.id.card_src);

        itemView.setOnClickListener(this); /*for single click listener*/
        itemView.setOnCreateContextMenuListener(this);/*for long press listener*/
    }

    /*passing item click callback from view to interface ItemClickListener */
    @Override
    public void onClick(View v) {
        /*if listener available */
        if(mItemClickListener!=null)    mItemClickListener.onClick(v,getAdapterPosition(),false);
    }

    /* for long press */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.setHeaderTitle("Select the Action");
        menu.add(0,0,getAdapterPosition(),"New");
    }


    /*for using callback of item click in OnBindViewHolder()*/
        private void seItemClickListener(ItemClickListener itemClickListener) {
            mItemClickListener=itemClickListener;
        }

    }
/*End of ViewHolder Class*/

   /*Member variables of adapter class */
   private Context mContext;
    private List<?> dataList;

    /*Constructor with click listener*/
    public ListItemAdapterTechSmall(Context mContext, List<?> dataList, ItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.mItemClickListener=itemClickListener;
    }


    /* for updating adapter on refresh*/
    public void updateAdapter(ArrayList<?> list){
        dataList=  list;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.home_body_recycler_fragment_card_grid, parent,false);

        return new ListItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {


       /* GlideApp.with(holder.image_row.getContext())
                .load(title_dataModel.getIcons(position))

                .placeholder(shimmerDrawable)

                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.image_row);
*/
       Glide.with(holder.cardImage.getContext())
               .load( ( (List<Article>) dataList).get(position).getUrlToImage())
                .thumbnail(0.1f)
               .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
               .into(holder.cardImage);


        holder.cardTitle.setText(
                ( (List<Article>) dataList).get(position).getTitle()  // casting for getting the getMethods for this model*//*
        );
        holder.cardContent.setText(
                ( (List<Article>) dataList).get(position).getDescription()
        );
        holder.cardAuthor.setText(
                ( (List<Article>) dataList).get(position).getAuthor()  // casting for getting the getMethods for this model*//*
        );
        holder.cardSource.setText(
                ( (List<Article>) dataList).get(position).getSource().getName()
        );
        holder.cardDate.setText(
                ( (List<Article>) dataList).get(position).getPublishedAt().substring(0,10)  // casting for getting the getMethods for this model*//*
        );

       /* //Another way not recommended to handle item click. Note if following code is running then activity will not get the callback as only one we receive the callback
        holder.seItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                Toast.makeText(mContext, "I am no:"+position, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    /* Interface for Item click callback */
    public interface ItemClickListener {

        void onClick(View view, int position, boolean isLongClick);
    }

}
