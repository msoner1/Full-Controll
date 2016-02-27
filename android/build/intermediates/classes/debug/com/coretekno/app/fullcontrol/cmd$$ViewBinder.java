// Generated code from Butter Knife. Do not modify!
package com.coretekno.app.fullcontrol;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class cmd$$ViewBinder<T extends com.coretekno.app.fullcontrol.cmd> implements ViewBinder<T> {
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
    view = finder.findRequiredView(source, 2131558541, "field 'navigationView'");
    target.navigationView = finder.castView(view, 2131558541, "field 'navigationView'");
    view = finder.findRequiredView(source, 2131558584, "field 'cmd_text'");
    target.cmd_text = finder.castView(view, 2131558584, "field 'cmd_text'");
    view = finder.findRequiredView(source, 2131558582, "field 'cmd_list'");
    target.cmd_list = finder.castView(view, 2131558582, "field 'cmd_list'");
    view = finder.findRequiredView(source, 2131558581, "field 'top_cmd'");
    target.top_cmd = finder.castView(view, 2131558581, "field 'top_cmd'");
    view = finder.findRequiredView(source, 2131558583, "field 'write_layout'");
    target.write_layout = finder.castView(view, 2131558583, "field 'write_layout'");
    view = finder.findRequiredView(source, 2131558585, "method 'button_send'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.button_send(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.pc_name = null;
    target.user_name = null;
    target.user_connect_id = null;
    target.toolbar = null;
    target.drawer = null;
    target.navigationView = null;
    target.cmd_text = null;
    target.cmd_list = null;
    target.top_cmd = null;
    target.write_layout = null;
  }
}
