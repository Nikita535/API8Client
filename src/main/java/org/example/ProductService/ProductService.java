package org.example.ProductService;


import com.example.grpc.GreetingServiceOuterClass;
import org.example.Client;
import org.example.Entity.Task;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public static JSONArray productToJson(GreetingServiceOuterClass.TaskResponse products){
        JSONArray object = new JSONArray();
        object.put(0, products.getId());
        object.put(1, products.getName());
        object.put(2, products.getDescription());
        object.put(3, products.getDate());
        return object;
    }

    public JSONArray productsToJson(GreetingServiceOuterClass.AllTasksResponse casesResponse){
        JSONArray products = new JSONArray();
        for (int i = 0; i < casesResponse.getCasesCount(); i++){
            products.put(i, productToJson(casesResponse.getCases(i)));
        }
        return products;
    }


    public Task addCase(Task entity)
    {
        GreetingServiceOuterClass.TaskResponse response = Client.addCase(entity);
        return new Task(response.getId(), response.getDate(), response.getName(), response.getDescription());
    }

    public void deleteCase(Long id){
        GreetingServiceOuterClass.Done response = Client.deleteCase(id);
    }

    public Task changeCase(Task entity){
        GreetingServiceOuterClass.TaskResponse response = Client.changeCase(entity);
        return new Task(response.getId(), response.getDate(), response.getName(), response.getDescription());
    }

    public Task getCaseById(Long id){
        GreetingServiceOuterClass.AllTasksResponse response = Client.allCases();
        for (int i = 0; i < response.getCasesCount(); i++){
            if (response.getCases(i).getId() == id){
                return new Task(response.getCases(i).getId(), response.getCases(i).getDate()
                        , response.getCases(i).getName(), response.getCases(i).getDescription());
            }
        }
        return new Task();
    }
}
