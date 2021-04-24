package com.example.movieapp.data.network;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.movieapp.Commons.ExecutorHelper;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// first parameter = room
// second parameter = api


public abstract class NetworkBoundResource<ResultType , RequestType> {
    private final MediatorLiveData<Resource<ResultType>> result
            = new MediatorLiveData<>();
    private ExecutorService executorService;
    protected NetworkBoundResource(){
        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data->{
            result.removeSource(dbSource);
            if(shouldFetch(data)){
                fetchFromNetwork(dbSource);
            }
            else{
                result.addSource(dbSource, newData->result.setValue(Resource.success(newData)));
            }
        });
        executorService = ExecutorHelper.getSingleThreadExecutorInstance();
    }



    private void fetchFromNetwork(final LiveData<ResultType> dbSource){
        result.addSource(dbSource, newData->result.setValue(Resource.loading(newData)));

        createCall().enqueue(new Callback<RequestType>() {
            @Override
            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
             result.removeSource(dbSource);
             saveResultandReinit(response.body());
            }

            @Override
            public void onFailure(Call<RequestType> call, Throwable t) {
                onFetchFailed();
                result.removeSource(dbSource);
                result.addSource(dbSource, newData->result.setValue(Resource.error(t.getMessage(), newData)));
            }
        });
    }

    private void saveResultandReinit(RequestType response){
      Future<Void> future = executorService.submit(() -> {
            saveCallResult(response);
            return null;
        });

        try {
            future.get();
            result.addSource(loadFromDb(), newData->result.setValue(Resource.success(newData)));
        } catch (ExecutionException e) {
            //log
            e.printStackTrace();
        } catch (InterruptedException e) {
            //log
            e.printStackTrace();
        }
        executorService.shutdown();

    }

    protected abstract void saveCallResult(RequestType item);


    protected abstract boolean shouldFetch(ResultType data);

    protected abstract LiveData<ResultType> loadFromDb();

    protected abstract Call<RequestType> createCall();

    protected void onFetchFailed(){

    }


    public final LiveData<Resource<ResultType>> getAsLiveData(){
        return result;
    }






}
