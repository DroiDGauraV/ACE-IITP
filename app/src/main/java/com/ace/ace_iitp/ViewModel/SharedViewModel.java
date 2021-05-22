package com.ace.ace_iitp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ace.ace_iitp.DataItems.EventsDataItems;
import com.ace.ace_iitp.DataItems.FeedStoriesData;
import com.ace.ace_iitp.DataItems.ImageItems;
import com.ace.ace_iitp.repo.OwnRepo;

import java.util.List;

public class SharedViewModel extends ViewModel implements OwnRepo.OnFirestoreTaskComplete{

    private MutableLiveData<List<EventsDataItems>> eventlistLiveData = new MutableLiveData<>();
    private MutableLiveData<List<FeedStoriesData>> storieslistLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ImageItems>> bannerListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ImageItems>> galleryLiveData = new MutableLiveData<>();


    //getter for events live data
    public LiveData<List<EventsDataItems>> getEventsListLiveData() {
        return eventlistLiveData;
    }

    //getter for stories live data
    public LiveData<List<FeedStoriesData>> getStorieslistLiveData() {
        return storieslistLiveData;
    }

    //getter of gallery
    public LiveData<List<ImageItems>> getGalleryLiveData() {
        return galleryLiveData;
    }

    //getter of banner
    public LiveData<List<ImageItems>> getBannerListLiveData() {
        return bannerListLiveData;
    }

    private OwnRepo repo = new OwnRepo(this);

    //constructor automatically calls for events data but when using for feed it would call the firestore every time
    public SharedViewModel() {
        //so left empty
    }

    public void callStoriesData () {
        repo.getStoriesData();
    }

    public void callEventsData () {
        repo.getEventsData();
    }

    public void callBannerData () {
        repo.getBannerData();
    }

    public void callGalleryData () {
        repo.getGalleryData();
    }

    //implementing interface methods here
    @Override
    public void eventsListDataAdded(List<EventsDataItems> eventsDataItemsList) {
        eventlistLiveData.setValue(eventsDataItemsList);
    }

    @Override
    public void storiesListDataAdded(List<FeedStoriesData> storiesDataList) {
        storieslistLiveData.setValue(storiesDataList);
    }

    @Override
    public void bannerListDataAdded(List<ImageItems> bannerDataList) {
        bannerListLiveData.setValue(bannerDataList);
    }

    @Override
    public void galleryListDataAdded(List<ImageItems> galleryDataList) {
        galleryLiveData.setValue(galleryDataList);
    }

    @Override
    public void onError(Exception e) {

    }
}
