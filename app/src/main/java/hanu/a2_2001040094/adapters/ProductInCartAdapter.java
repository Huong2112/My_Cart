package hanu.a2_2001040094.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hanu.a2_2001040094.R;
import hanu.a2_2001040094.db.ProductRepository;
import hanu.a2_2001040094.fragment.ImageDownload;
import hanu.a2_2001040094.interfaces.CalculatSum;
import hanu.a2_2001040094.models.Product;

public class ProductInCartAdapter extends RecyclerView.Adapter<ProductInCartAdapter.ProductInCartViewHolder> {
    private Context context;
   private CalculatSum calculatSum;
    private List<Product> products;

    ProductRepository productRepository;

    ImageDownload imageDownloadFragment = new ImageDownload();

    public ProductInCartAdapter(Context context, CalculatSum calculatSum) {
        this.context = context;
        this.calculatSum = calculatSum;

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override


    public ProductInCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_cart, parent, false);
        return new ProductInCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductInCartViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Product product = products.get(position);
        productRepository = ProductRepository.getInstance(context);
        if (product == null){
            return;
        }

        imageDownloadFragment.setImagview(holder.cimg, product.getThumbnail());
         holder.cname.setText(product.getName());
         holder.cprice.setText(String.valueOf(product.getUnitPrice()));
         holder.cquantity.setText(String.valueOf(product.getQuantity()));
         holder.camount.setText(String.valueOf(product.getUnitPrice()*product.getQuantity()));

         holder.acs.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int ascquant = productRepository.getQuantInDb(product.getId()) + 1;
                 productRepository.editProduct(product.getId(), ascquant);
                 holder.cquantity.setText(String.valueOf(ascquant));
                 holder.camount.setText(String.valueOf(product.getUnitPrice()*ascquant));
                 int amount = productRepository.getTotalAmount();
                 calculatSum.OnClick(amount);
             }
         });

         holder.desc.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                     int descquant = productRepository.getQuantInDb(product.getId()) - 1;
                     if (descquant == 0){
                         products.remove(position);
                         notifyDataSetChanged();
                         productRepository.removeProduct(product.getId());
                         int amount = productRepository.getTotalAmount();
                         calculatSum.OnClick(amount);
                     }
                     else {
                         productRepository.editProduct(product.getId(), descquant);
                         holder.cquantity.setText(String.valueOf(descquant));
                         holder.camount.setText(String.valueOf(product.getUnitPrice() * descquant));
                         int amount = productRepository.getTotalAmount();
                         calculatSum.OnClick(amount);
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

    public class ProductInCartViewHolder extends RecyclerView.ViewHolder{
        private ImageView cimg;
        private TextView cname, cprice, cquantity, camount;

        private ImageButton acs , desc;

        public ProductInCartViewHolder(@NonNull View itemView) {
            super(itemView);
            cimg = itemView.findViewById(R.id.img);
            cname = itemView.findViewById(R.id.nameincart);
            cquantity = itemView.findViewById(R.id.quant);
            cprice = itemView.findViewById(R.id.cartprice);
            camount = itemView.findViewById(R.id.amount);
            acs = itemView.findViewById(R.id.plus);
            desc = itemView.findViewById(R.id.minus);
        }
    }
}
