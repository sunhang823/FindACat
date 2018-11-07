package edu.gwu.findacat

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import edu.gwu.findacat.generated.PetFinder.PetItem
import kotlinx.android.synthetic.main.cat_row.view.*

class PetsAdapter(var petItems:List<PetItem>, val clickListener: onPetClickListener):RecyclerView.Adapter<PetsAdapter.ViewHolder>(){
    interface onPetClickListener{
        fun onPetClick(pet:PetItem, petView:View)
    }
    override fun getItemCount():Int{
        return petItems.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.cat_row,parent,false)
        return ViewHolder(cellForRow)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val petItem = petItems[position]
        holder.bind(petItem,clickListener)
    }
    inner class ViewHolder(val view:View):RecyclerView.ViewHolder(view){
        private val TAG = "PetAdapter"
        fun bind(petItem:PetItem,listener: onPetClickListener)= with(itemView){
            cat_name_text_view.text=petItem.name?.T
            petItem.media?.photos?.photo?.let {
                var uri = Uri.parse(it[2]?.T)
                Picasso.get().load(uri).into(cat_image_view, object: com.squareup.picasso.Callback {
                    override fun onSuccess() {
                    }
                    override fun onError(e: java.lang.Exception?) {
                    }
                })
            }
            setOnClickListener{
                listener.onPetClick(petItem,it)
            }
        }
    }
}

