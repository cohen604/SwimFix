import 'dart:collection';

import 'package:synchronized/synchronized.dart';

class ConcurrentQueue<T> {
  Lock _locker;
  Queue<T> queue;

  ConcurrentQueue() {
    _locker = new Lock();
    queue = new Queue();
  }

  Future<bool> enqueue(T element) async {
    await _locker.synchronized(() => queue.add(element));
    return true;
  }

  Future<T> dequeue() async {
    T output;
    await _locker.synchronized(() {
      if(queue.isNotEmpty) {
        output = queue.removeFirst();
      }
    });
    return output;
  }

  Future<bool> isEmpty() async {
    bool output = false;
    await _locker.synchronized(() {
      output = queue.isEmpty;
    });
    return output;
  }

}