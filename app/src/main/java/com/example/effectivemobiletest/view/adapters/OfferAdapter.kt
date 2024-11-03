package com.example.effectivemobiletest.view.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.dto.Offer
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.ItemOfferBinding

class OfferAdapter : RecyclerView.Adapter<OfferAdapter.OfferHolder>() {

    private var offerList: List<Offer> = listOf()

    class OfferHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemOfferBinding.bind(item)

        fun bind(offer: Offer) {

            binding.title.maxLines = 2
            binding.icon.visibility = View.VISIBLE
            when (offer.id) {
                "near_vacancies" -> {
                    binding.icon.setImageResource(R.drawable.ic_offer_near_vacancies)
                    binding.icon.setBackgroundResource(R.drawable.bg_circle_blue)
                }

                "level_up_resume" -> {
                    binding.icon.setImageResource(R.drawable.ic_offer_level_up_resume)
                    binding.icon.setBackgroundResource(R.drawable.bg_circle_green)
                }

                "temporary_job" -> {
                    binding.icon.setImageResource(R.drawable.ic_offer_temporary_job)
                    binding.icon.setBackgroundResource(R.drawable.bg_circle_green)
                }

                else -> {
                    binding.icon.visibility = View.GONE
                    binding.title.maxLines = 3
                }
            }

            binding.title.text = offer.title.trimStart()

            if (offer.button == null) {
                binding.buttonText.visibility = View.GONE
            } else {
                binding.buttonText.visibility = View.VISIBLE
                binding.buttonText.text = offer.button!!.text
            }

            binding.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(offer.link))
                itemView.context.startActivity(intent)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_offer, parent, false)
        return OfferHolder(view)
    }

    override fun onBindViewHolder(holder: OfferHolder, position: Int) {
        holder.bind(offerList[position])
    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(list: List<Offer>?) {
        offerList = list ?: emptyList()
        notifyDataSetChanged()
    }

}