// Generated code from Butter Knife. Do not modify!
package com.coretekno.app.fullcontrol;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.coretekno.app.fullcontrol.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findOptionalView(source, 2131558565, null);
    target.pc_name = finder.castView(view, 2131558565, "field 'pc_name'");
    view = finder.findOptionalView(source, 2131558574, null);
    target.pc_status = finder.castView(view, 2131558574, "field 'pc_status'");
    view = finder.findOptionalView(source, 2131558578, null);
    target.ram_value = finder.castView(view, 2131558578, "field 'ram_value'");
    view = finder.findOptionalView(source, 2131558582, null);
    target.cpu_value = finder.castView(view, 2131558582, "field 'cpu_value'");
    view = finder.findOptionalView(source, 2131558585, null);
    target.temp_value = finder.castView(view, 2131558585, "field 'temp_value'");
    view = finder.findRequiredView(source, 2131558606, "field 'user_name'");
    target.user_name = finder.castView(view, 2131558606, "field 'user_name'");
    view = finder.findRequiredView(source, 2131558607, "field 'user_connect_id'");
    target.user_connect_id = finder.castView(view, 2131558607, "field 'user_connect_id'");
    view = finder.findRequiredView(source, 2131558540, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131558540, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131558539, "field 'drawer'");
    target.drawer = finder.castView(view, 2131558539, "field 'drawer'");
    view = finder.findRequiredView(source, 2131558541, "field 'navigationView'");
    target.navigationView = finder.castView(view, 2131558541, "field 'navigationView'");
    view = finder.findRequiredView(source, 2131558562, "field 'fab' and method 'fab_click'");
    target.fab = finder.castView(view, 2131558562, "field 'fab'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.fab_click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558573, "method 'power_click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.power_click(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.pc_name = null;
    target.pc_status = null;
    target.ram_value = null;
    target.cpu_value = null;
    target.temp_value = null;
    target.user_name = null;
    target.user_connect_id = null;
    target.toolbar = null;
    target.drawer = null;
    target.navigationView = null;
    target.fab = null;
  }
}
