package hanu.a2_2001040094.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hanu.a2_2001040094.R;
import hanu.a2_2001040094.db.ProductRepository;
import hanu.a2_2001040094.fragment.ImageDownload;
import hanu.a2_2001040094.models.Product;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> products;
    ProductRepository productRepository;
    ImageDownload imageDownloadFragment;

    public ProductsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        productRepository = new ProductRepository(this.context);
        if (product == null){
            return;
        }
        imageDownloadFragment = new ImageDownload();
        String url = product.getThumbnail();
        imageDownloadFragment.setImagview(holder.prdImg, url);
        holder.tvName.setText(product.getName());
        String price ="đ "+  String.valueOf(product.getUnitPrice());
        holder.tvPrice.setText(price);
        holder.cartImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idInCart = product.getId();
                if (productRepository.all().size() == 0){
                    productRepository.addProduct(idInCart, product.getName(), product.getThumbnail(), product.getCategory(), product.getUnitPrice(), 1);
                } else if (productRepository.getProductByID(idInCart).size() == 0) {
                    productRepository.addProduct(idInCart, product.getName(), product.getThumbnail(), product.getCategory(), product.getUnitPrice(), 1);
                    Toast.makeText(context, "Added to cart!", Toast.LENGTH_SHORT).show();
                } else {
                    int ascquant = productRepository.getQuantInDb(idInCart)+1;
                    productRepository.editProduct(idInCart, ascquant);
                    Toast.makeText(context, "Added to cart!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if (products != null){
            return products.size();
        }
        return 0;
    }

    public class  ProductViewHolder extends RecyclerView.ViewHolder{

        private ImageView prdImg;
        private ImageButton cartImg;
        private TextView tvName, tvPrice;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            prdImg = itemView.findViewById(R.id.imgProduct);
            cartImg = itemView.findViewById(R.id.cart);
            tvName = itemView.findViewById(R.id.nameProduct);
            tvPrice = itemView.findViewById(R.id.price);
        }
    }
}
