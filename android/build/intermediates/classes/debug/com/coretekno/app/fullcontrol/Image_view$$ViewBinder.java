// Generated code from Butter Knife. Do not modify!
package com.coretekno.app.fullcontrol;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Image_view$$ViewBinder<T extends com.coretekno.app.fullcontrol.Image_view> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findOptionalView(source, 2131558568, null);
    target.pc_name = finder.castView(view, 2131558568, "field 'pc_name'");
    view = finder.findRequiredView(source, 2131558642, "field 'user_name'");
    target.user_name = finder.castView(view, 2131558642, "field 'user_name'");
    view = finder.findRequiredView(source, 2131558643, "field 'user_connect_id'");
    target.user_connect_id = finder.castView(view, 2131558643, "field 'user_connect_id'");
    view = finder.findRequiredView(source, 2131558540, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131558540, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131558539, "field 'drawer'");
    target.drawer = finder.castView(view, 2131558539, "field 'drawer'");
    view = finder.findRequiredView(source, 2131558595, "field 'image' and method 'image_click'");
    target.image = finder.castView(view, 2131558595, "field 'image'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.image_click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558594, "field 'pc_image'");
    target.pc_image = finder.castView(view, 2131558594, "field 'pc_image'");
    view = finder.findRequiredView(source, 2131558541, "field 'navigationView'");
    target.navigationView = finder.castView(view, 2131558541, "field 'navigationView'");
    view = finder.findRequiredView(source, 2131558596, "method 'download_image'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.download_image();
        }
      });
  }

  @Override public void unbind(T target) {
    target.pc_name = null;
    target.user_name = null;
    target.user_connect_id = null;
    target.toolbar = null;
    target.drawer = null;
    target.image = null;
    target.pc_image = null;
    target.navigationView = null;
  }
}
