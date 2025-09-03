
import { request } from '@/utils'

export default {
  create: data => request.post('/user/add', data),
  read: (params = {}) => request.get('/user/getPage', { params }),
  update: data => request.patch(`/user/update`, data),
  delete: id => request.delete(`/user/${id}`),
  resetPwd: (id, data) => request.patch(`/user/password/reset`, data),

  getAllRoles: () => request.get('/role/getList?enable=1'),
}
