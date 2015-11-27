package barqsoft.footballscores.service;

import android.content.Intent;
import android.widget.Adapter;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * WidgetService implements RemoteViewsService for the football scores collection widget
 * Created by ereinecke on 11/27/15.
 */
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        // return (new AppWidgetListFactory(this.getApplicationContext(), intent));
        return null; // TODO: this is a placeholder
    }
}

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    /**
     * Called when your factory is first constructed. The same factory may be shared across
     * multiple RemoteViewAdapters depending on the intent passed.
     */
    public void onCreate() {

    }

    /**
     * Called when notifyDataSetChanged() is triggered on the remote adapter. This allows a
     * RemoteViewsFactory to respond to data changes by updating any internal references.
     *
     * Note: expensive tasks can be safely performed synchronously within this method. In the
     * interim, the old data will be displayed within the widget.
     *
     * @see android.appwidget.AppWidgetManager#notifyAppWidgetViewDataChanged(int[], int)
     */
    public void onDataSetChanged(){

    }

    /**
     * Called when the last RemoteViewsAdapter that is associated with this factory is
     * unbound.
     */
    public void onDestroy() {

    }

    /**
     * See {@link Adapter#getCount()}
     *
     * @return Count of items.
     */
    public int getCount() {
        return -1; // TODO: this is a placeholder
    };

    /**
     * See {@link Adapter#getView(int, android.view.View, android.view.ViewGroup)}.
     *
     * Note: expensive tasks can be safely performed synchronously within this method, and a
     * loading view will be displayed in the interim. See {@link #getLoadingView()}.
     *
     * @param position The position of the item within the Factory's data set of the item whose
     *        view we want.
     * @return A RemoteViews object corresponding to the data at the specified position.
     */
    public RemoteViews getViewAt(int position){
        return null; // TODO: this is a placeholder
    }

    /**
     * This allows for the use of a custom loading view which appears between the time that
     * {@link #getViewAt(int)} is called and returns. If null is returned, a default loading
     * view will be used.
     *
     * @return The RemoteViews representing the desired loading view.
     */
    public RemoteViews getLoadingView(){
        return null; // TODO: this is a placeholder
    }

    /**
     * See {@link Adapter#getViewTypeCount()}.
     *
     * @return The number of types of Views that will be returned by this factory.
     */
    public int getViewTypeCount() {
        return -1; // TODO: this is a placeholder
    }

    /**
     * See {@link Adapter#getItemId(int)}.
     *
     * @param position The position of the item within the data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    public long getItemId(int position) {
        return -1; // TODO: this is a placeholder
    }

    /**
     * See {@link Adapter#hasStableIds()}.
     *
     * @return True if the same id always refers to the same object.
     */
    public boolean hasStableIds() {
        return false; // TODO: this is a placeholder
    }

}
