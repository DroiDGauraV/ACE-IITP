package com.ace.ace_iitp.repo;

import com.ace.ace_iitp.DataItems.EventsDataItems;
import com.ace.ace_iitp.DataItems.FeedStoriesData;
import com.ace.ace_iitp.DataItems.ImageItems;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.Objects;

public class OwnRepo {

    private OnFirestoreTaskComplete onFirestoreTaskComplete;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private Query activeEventsList = firebaseFirestore
            .collection("ActiveEventsList").orderBy("date", Query.Direction.DESCENDING);
    private Query activeStoriesList = firebaseFirestore
            .collection("ActiveStoriesList").orderBy("date", Query.Direction.DESCENDING);
    private Query bannerList = firebaseFirestore.collection("BannersList")
            .orderBy("order", Query.Direction.ASCENDING);
    private Query galleryList = firebaseFirestore.collection("GalleryList");


    public OwnRepo(OnFirestoreTaskComplete onFirestoreTaskComplete) {
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }

    public void getEventsData () {
        activeEventsList.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onFirestoreTaskComplete.eventsListDataAdded(Objects.requireNonNull(task.getResult()).toObjects(EventsDataItems.class));
            } else {
                onFirestoreTaskComplete.onError(task.getException());
            }
        });
    }

    public void getStoriesData () {
        activeStoriesList.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onFirestoreTaskComplete.storiesListDataAdded(Objects.requireNonNull(task.getResult()).toObjects(FeedStoriesData.class));
            } else  {
                onFirestoreTaskComplete.onError(task.getException());
            }
        });
    }

    public void getBannerData () {
        bannerList.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onFirestoreTaskComplete.bannerListDataAdded(Objects.requireNonNull(task.getResult()).toObjects(ImageItems.class));
            } else {
                onFirestoreTaskComplete.onError(task.getException());
            }
        });
    }

    public void getGalleryData () {
        galleryList.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onFirestoreTaskComplete.galleryListDataAdded(Objects.requireNonNull(task.getResult()).toObjects(ImageItems.class));
            } else {
                onFirestoreTaskComplete.onError(task.getException());
            }
        });
    }

    public interface OnFirestoreTaskComplete {
        void eventsListDataAdded (List<EventsDataItems> eventsDataItemsList);
        void storiesListDataAdded (List<FeedStoriesData> storiesDataList);
        void bannerListDataAdded (List<ImageItems> bannerDataList);
        void galleryListDataAdded (List<ImageItems> galleryDataList);
        void onError (Exception e);
    }
}
