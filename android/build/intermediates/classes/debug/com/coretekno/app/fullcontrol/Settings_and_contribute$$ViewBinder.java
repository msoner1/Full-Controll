// Generated code from Butter Knife. Do not modify!
package com.coretekno.app.fullcontrol;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Settings_and_contribute$$ViewBinder<T extends com.coretekno.app.fullcontrol.Settings_and_contribute> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558642, "field 'user_name'");
    target.user_name = finder.castView(view, 2131558642, "field 'user_name'");
    view = finder.findRequiredView(source, 2131558643, "field 'user_connect_id'");
    target.user_connect_id = finder.castView(view, 2131558643, "field 'user_connect_id'");
    view = finder.findRequiredView(source, 2131558540, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131558540, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131558539, "field 'drawer'");
    target.drawer = finder.castView(view, 2131558539, "field 'drawer'");
    view = finder.findRequiredView(source, 2131558541, "field 'navigationView'");
    target.navigationView = finder.castView(view, 2131558541, "field 'navigationView'");
    view = finder.findRequiredView(source, 2131558563, "field 'viewStub'");
    target.viewStub = finder.castView(view, 2131558563, "field 'viewStub'");
  }

  @Override public void unbind(T target) {
    target.user_name = null;
    target.user_connect_id = null;
    target.toolbar = null;
    target.drawer = null;
    target.navigationView = null;
    target.viewStub = null;
  }
}
